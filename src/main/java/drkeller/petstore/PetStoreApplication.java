package drkeller.petstore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.ResourceUtils;

import drkeller.petstore.model.DbApiKeyAuth;
import drkeller.petstore.repository.ApiKeyAuthRepository;


@SpringBootApplication
public class PetStoreApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PetStoreApplication.class);

    public static Properties PROPERTIES;

	@Autowired
	private ApiKeyAuthRepository apiKeyAuthRepository;
    
	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);
	}
	
	public static String getProperty(String key) throws IOException {
		if (PROPERTIES == null) {
			fetchProperties();
		}
		
		return PROPERTIES.getProperty(key);
	}
	
    public static void fetchProperties() throws IOException{
        PROPERTIES = new Properties();
        URL resource = ResourceUtils.getURL("classpath:application.properties");

        try (InputStream in = resource.openStream();) {
        	PROPERTIES .load(in);
        }
    }
    
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws Exception {
		LOG.info("-------------> hello world, I have just started up <-----------");

		LOG.info("populates API KEYs");
	    
	    List<String> scopes = new ArrayList<String>();
	    scopes.add("sc_create");
	    scopes.add("sc_update");
	    scopes.add("sc_delete");
	    
	    DbApiKeyAuth apiKeyAuth = new DbApiKeyAuth();
	    
//	    apiKeyAuth.setApiKey(UUID.randomUUID().toString());
	    apiKeyAuth.setApiKey("3cb29c50-127f-4c71-a7d2-3e6732c3d41f");
	    apiKeyAuth.setName("petstore - client");
	    apiKeyAuth.setTenantId("petstore-demo");
	    apiKeyAuth.encodeScopes(scopes);
	    apiKeyAuthRepository.save(apiKeyAuth);

	    scopes = new ArrayList<String>();
	    scopes.add("sc_list");
	    
	    apiKeyAuth = new DbApiKeyAuth();
//	    apiKeyAuth.setApiKey(UUID.randomUUID().toString());
	    apiKeyAuth.setApiKey("318ab1d8-74be-4571-aa15-a95052d92c0b");
	    apiKeyAuth.setName("petstore - supervisor");
	    apiKeyAuth.setTenantId("petstore-demo");
	    apiKeyAuth.encodeScopes(scopes);
	    apiKeyAuthRepository.save(apiKeyAuth);

	    List<DbApiKeyAuth> auths = apiKeyAuthRepository.findAll();
	    for (DbApiKeyAuth dbApiKeyAuth : auths) {
	    	LOG.info("ApiKeyAuth: " + dbApiKeyAuth.toString());
		}

	}
	
}
