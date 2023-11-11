package com.oss2.dasom.auth.oauth;

import com.oss2.dasom.auth.provider.NaverOauthConfig;
import com.oss2.dasom.auth.provider.OAuthServerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NaverAuthCodeRequestUrl implements OAuthCodeRequestUrl {

    private final NaverOauthConfig naverOauthConfig;

    @Override
    public OAuthServerProvider currentServer() {
        return OAuthServerProvider.NAVER;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", naverOauthConfig.clientId())
                .queryParam("redirect_uri", naverOauthConfig.redirectUri())
                .queryParam("state", "123")
                .build()
                .toUriString();


    }
}
