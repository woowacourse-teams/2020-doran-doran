package com.grasshouse.dorandoran.config.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class OAuth2Configuration {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
        @Value("${kakao.client-id}") String clientId,
        @Value("${kakao.client-secret}") String clientSecret) {
        final ClientRegistration clientRegistration = CustomOAuth2Provider.KAKAO
            .getBuilder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build();

        return new InMemoryClientRegistrationRepository(Collections.singletonList(
            clientRegistration
        ));
    }

}
