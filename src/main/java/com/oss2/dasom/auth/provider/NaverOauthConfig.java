package com.oss2.dasom.auth.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver")
public record NaverOauthConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String state,
        String[] scope
) {

}

