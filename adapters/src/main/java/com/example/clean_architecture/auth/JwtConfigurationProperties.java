package com.example.clean_architecture.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
 class JwtConfigurationProperties {

    private String secret;
    private long validity;

    String getSecret() {
        return secret;
    }

    long getValidity() {
        return validity;
    }
}