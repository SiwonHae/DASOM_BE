package com.oss2.dasom.auth.oauth;

import com.oss2.dasom.auth.provider.OAuthServerProvider;
import org.springframework.stereotype.Component;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class OAuthCodeRequestUrlComposite {

    private final Map<OAuthServerProvider, OAuthCodeRequestUrl> mapping;

    public OAuthCodeRequestUrlComposite(Set<OAuthCodeRequestUrl> providers) {
        mapping = providers.stream()
                .collect(toMap(
                        OAuthCodeRequestUrl::currentServer,
                        identity()
                ));
    }

    public String provide(OAuthServerProvider oAuthServerProvider) {
        return getProvider(oAuthServerProvider).provide();
    }

    public OAuthCodeRequestUrl getProvider(OAuthServerProvider oAuthServerProvider) {
        return Optional.ofNullable(mapping.get(oAuthServerProvider))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 입니다."));
    }
}