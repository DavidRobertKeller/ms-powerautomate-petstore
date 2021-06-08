package drkeller.petstore.swagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;

import drkeller.petstore.dto.PetWebhookResponse;
import drkeller.petstore.security.ApiKeyAuthConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.LoginEndpointBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {                                    

    private static final Logger LOG = LoggerFactory.getLogger(SpringFoxConfig.class);

	@Autowired
	@Value("${security.idp.enabled}")
	private Boolean idpEnabled;

	@Autowired
	@Value("${swaggerui.oauth2.realm}")
	private String oauth2Realm;

	@Autowired
	@Value("${swaggerui.oauth2.clientid}")
	private String clientId;

	@Autowired
	@Value("${swaggerui.oauth2.appname}")
	private String oauth2AppName;

	@Autowired
	@Value("${swaggerui.oauth2.authentication.url}")
	private String oauth2Url;
	
	@Autowired
	private TypeResolver typeResolver;
		
	@Bean
	public Docket apis() {
		Docket docklet 
			= new Docket(DocumentationType.SWAGGER_2)
			    .select()
				.apis(RequestHandlerSelectors.basePackage("drkeller.petstore"))
				.paths(PathSelectors.any())
				.build();
		
		if (idpEnabled) {
			docklet
				.securitySchemes(buildSecurityScheme())
				.securityContexts(buildSecurityContext());
		} else {
			docklet
				.securitySchemes(
					Arrays.asList(
							new ApiKey(ApiKeyAuthConfiguration.AUTH_HEADER_NAME, 
									ApiKeyAuthConfiguration.AUTH_HEADER_NAME, 
									"header"))
				);
		}
		
		docklet
			.apiInfo(apiInfo())
	 		.additionalModels(typeResolver.resolve(PetWebhookResponse.class)); 
		 
		return docklet;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Ms Power Automate PetStore")
				.description("Ms Power Automate PetStore for developers")
				.version("0.0.1")
				.build();
	}
	
    @Bean
    public SecurityConfiguration securityConfiguration() {

        Map<String, Object> additionalQueryStringParams=new HashMap<>();
        additionalQueryStringParams.put("nonce","123456");

        return SecurityConfigurationBuilder.builder()
            .clientId(this.clientId)
            .realm(this.oauth2Realm)
            .appName(this.oauth2AppName)
            .additionalQueryStringParams(additionalQueryStringParams)
            .build();
    }
	 
    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(
        		SecurityReference.builder()
	        		.reference("oauth2")
	        		.scopes(scopes()
	        		.toArray(new AuthorizationScope[]{}))
	        		.build());

        SecurityContext context = SecurityContext
        		.builder()
        		.forPaths(Predicates.alwaysTrue())
        		.securityReferences(securityReferences)
        		.build();

        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        return ret;
    }

    private List<SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();

        // add API_KEY authentication
        lst.add(
				new ApiKey(ApiKeyAuthConfiguration.AUTH_HEADER_NAME, 
						ApiKeyAuthConfiguration.AUTH_HEADER_NAME, 
						"header"));

        // add OAuth2 authentication
        LoginEndpoint login = new LoginEndpointBuilder().url(oauth2Url).build();
        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "acces_token"));
        lst.add(new OAuth("oauth2", scopes(), gTypes));

        return lst;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String scope[] = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(
                		new AuthorizationScopeBuilder()
                		.scope(scope[0])
                		.description(scope[1])
                		.build());
            } else {
            	LOG.info("[WARN] Scope '{" + scopeItem + "}' is not valid (format is scope=description)");
            }
        }

        return scopes;
    }

    @Bean
    public PropertyBuilderPlugin modelPropertiyPlugin() {
        return new PropertyBuilderPlugin();
    }
}