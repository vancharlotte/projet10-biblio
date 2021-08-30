package com.example.librarybatchnotif.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;


@Configuration
public class RestTemplateConfig {

    @Bean
    protected ClientCredentialsResourceDetails oAuthDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId("batchnotif");
        details.setClientSecret("secret");
        details.setAccessTokenUri("http://localhost:9191/oauth/token");
        details.setGrantType("client_credentials");
        return details;
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(ClientCredentialsResourceDetails details) {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
        return oAuth2RestTemplate;
    }

}
