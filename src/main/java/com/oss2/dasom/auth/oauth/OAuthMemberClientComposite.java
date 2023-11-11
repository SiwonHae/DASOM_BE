package com.oss2.dasom.auth.oauth;

import com.oss2.dasom.auth.provider.OAuthServerProvider;
import com.oss2.dasom.domain.OAuthMemberClient;
import com.oss2.dasom.domain.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class OAuthMemberClientComposite {

    private final Map<OAuthServerProvider, OAuthMemberClient> mapping;

    public OAuthMemberClientComposite(Set<OAuthMemberClient> clients) {
        mapping = clients.stream()
                .collect(toMap(
                        OAuthMemberClient::currentServer,
                        identity()
                ));
    }

    public User fetch(OAuthServerProvider oAuthServerProvider, String authCode) {
        return getClient(oAuthServerProvider).fetch(authCode);
    }

    private OAuthMemberClient getClient(OAuthServerProvider oAuthServerProvider) {
        return Optional.ofNullable(mapping.get(oAuthServerProvider))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }
}