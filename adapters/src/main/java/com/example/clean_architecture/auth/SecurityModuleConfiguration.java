package com.example.clean_architecture.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class SecurityModuleConfiguration {

    @Bean
    TokenFacade tokenFacade(JwtConfigurationProperties jwtConfigurationProperties) {
        return new TokenFacade(jwtConfigurationProperties);
    }
}