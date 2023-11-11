package com.oss2.dasom.auth.oauth;

import com.oss2.dasom.auth.provider.KakaoOauthConfig;
import com.oss2.dasom.auth.provider.OAuthServerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrl implements OAuthCodeRequestUrl {

    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public OAuthServerProvider currentServer() {
        return OAuthServerProvider.KAKAO;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoOauthConfig.clientId())
                .queryParam("redirect_uri", kakaoOauthConfig.redirectUri())
                .queryParam("scope", String.join(",", kakaoOauthConfig.scope()))
                .toUriString();
    }
}
