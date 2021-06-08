package drkeller.petstore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Value("${security.idp.enabled}")
	private Boolean idpEnabled;

	private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/",
            "/csrf",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
           
          if (idpEnabled) {
      		http.csrf().disable()
				.httpBasic().disable()
				.formLogin().disable()
				.logout().disable()
				.cors()
			    .and()
	            	.authorizeRequests()
	            	.antMatchers(AUTH_WHITELIST).permitAll()  
	            	.antMatchers("/**").authenticated()
	            .and()
	              .oauth2ResourceServer()
	                .jwt();
          } else {
        		http.csrf().disable()
  				.httpBasic().disable()
  				.formLogin().disable()
  				.logout().disable()
  				.cors()
  			    .and()
  	            	.authorizeRequests()
  	            	.antMatchers(AUTH_WHITELIST).permitAll()  
	            	.antMatchers("/api/v1/**").permitAll()  
  	            	.antMatchers("/**").authenticated();
          }
    }
}
