package com.microservice.user.service.config;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

	private OAuth2AuthorizedClientManager manager;
	
	
	
	public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, 
			ClientHttpRequestExecution execution)
			throws IOException {
		OAuth2AuthorizeRequest auth2AuthorizeRequest = OAuth2AuthorizeRequest
				.withClientRegistrationId("my-internal-client")
				.principal("interval")
				.build();
		String tokenValue = manager.authorize(auth2AuthorizeRequest)
				.getAccessToken()
				.getTokenValue();
		request.getHeaders().add("Authorization", "Bearer "+tokenValue);
		return execution.execute(request, body);
	}

}
