package com.oss2.dasom.domain;

import com.oss2.dasom.auth.provider.OAuthServerProvider;

public interface OAuthMemberClient {

    OAuthServerProvider currentServer();

    User fetch(String code);
}
