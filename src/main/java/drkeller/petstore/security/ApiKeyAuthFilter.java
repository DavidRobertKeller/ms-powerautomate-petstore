package drkeller.petstore.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

	private final String headerName;

	public ApiKeyAuthFilter(final String headerName) {
		this.headerName = headerName;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return request.getHeader(headerName);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		// No creds when using API key
		return null;
	}
}
