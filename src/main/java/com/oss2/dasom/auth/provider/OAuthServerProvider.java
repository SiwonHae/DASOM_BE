package com.oss2.dasom.auth.provider;

import java.util.Locale;

public enum OAuthServerProvider {

    NAVER, KAKAO;

    public static OAuthServerProvider fromName(String provider) {
        return OAuthServerProvider.valueOf(provider.toUpperCase(Locale.ENGLISH));
    }

}
