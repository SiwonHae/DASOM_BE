package com.oss2.dasom.auth.oauth;

import com.oss2.dasom.auth.provider.OAuthServerProvider;

public interface OAuthCodeRequestUrl {
    OAuthServerProvider currentServer();

    String provide();
}
