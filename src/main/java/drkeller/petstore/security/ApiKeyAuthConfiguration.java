package drkeller.petstore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(1)
public class ApiKeyAuthConfiguration extends WebSecurityConfigurerAdapter {
	public static final String AUTH_HEADER_NAME = "API_KEY";

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ApiKeyAuthFilter filter = new ApiKeyAuthFilter(AUTH_HEADER_NAME);
		filter.setAuthenticationManager(new ApiKeyAuthManager(dataSource));

		http.antMatcher("/api/v1/**")
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(filter).authorizeRequests()
			.anyRequest().authenticated();
	}
}
