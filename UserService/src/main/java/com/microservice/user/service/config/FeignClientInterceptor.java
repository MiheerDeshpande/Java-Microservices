package com.microservice.user.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {

	@Autowired
	private OAuth2AuthorizedClientManager manager;
	@Override
	public void apply(RequestTemplate template) {
		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
				.withClientRegistrationId("my-internal-client")
				.principal("interval")
				.build();
		String tokenValue = manager.authorize(request).getAccessToken().getTokenValue();
		
		template.header("Authorization", "Bearer "+tokenValue);
		
	}

}
