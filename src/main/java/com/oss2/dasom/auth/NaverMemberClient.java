package com.oss2.dasom.auth;

import com.oss2.dasom.auth.provider.KakaoOauthConfig;
import com.oss2.dasom.auth.provider.NaverOauthConfig;
import com.oss2.dasom.auth.provider.OAuthServerProvider;
import com.oss2.dasom.domain.OAuthMemberClient;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.KakaoMemberResponse;
import com.oss2.dasom.dto.KakaoToken;
import com.oss2.dasom.dto.NaverMemberResponse;
import com.oss2.dasom.dto.NaverToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class NaverMemberClient implements OAuthMemberClient {

    private final NaverApiClient naverApiClient;
    private final NaverOauthConfig naverOauthConfig;

    @Override
    public OAuthServerProvider currentServer() {
        return OAuthServerProvider.NAVER;
    }

    @Override
    public User fetch(String authCode) {
        NaverToken tokenInfo = naverApiClient.fetchToken(tokenRequestParams(authCode)); // (1)
        NaverMemberResponse naverMemberResponse =
                naverApiClient.fetchMember("Bearer " + tokenInfo.accessToken());  // (2)
        return naverMemberResponse.toDomain();  // (3)
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverOauthConfig.clientId());
        params.add("redirect_uri", naverOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", naverOauthConfig.clientSecret());
        return params;
    }
}