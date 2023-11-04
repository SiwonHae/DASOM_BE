package com.oss2.dasom.auth.provider;

import com.oss2.dasom.domain.Gender;

import java.time.LocalDate;
import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{
    private Map<String, Object> attributes; //OAuth2User.getAttributes();
    private Map<String, Object> attributesResponse;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
        this.attributesResponse = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return attributesResponse.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return attributesResponse.get("email").toString();
    }

    @Override
    public String getRealname() {
        return attributesResponse.get("name").toString();
    }

    @Override
    public int getAge() {
        String birthYearString = attributesResponse.get("birthyear").toString();
        int birthYear = Integer.parseInt(birthYearString);
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - birthYear + 1;
        return age;
    }

    @Override
    public Gender getGender() {
        String gen = attributesResponse.get("gender").toString();
        if ("M".equals(gen)) {
            return Gender.MALE;
        } else if ("F".equals(gen)) {
            return Gender.FEMALE;
        }
        return null;
    }

}