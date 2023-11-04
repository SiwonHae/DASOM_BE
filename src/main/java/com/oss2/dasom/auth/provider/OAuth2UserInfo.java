package com.oss2.dasom.auth.provider;

import com.oss2.dasom.domain.Gender;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();
    String getProviderId();
    String getProvider();
    String getEmail();
    String getRealname();
    int getAge();

    Gender getGender();
}