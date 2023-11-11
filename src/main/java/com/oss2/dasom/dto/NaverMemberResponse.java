package com.oss2.dasom.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oss2.dasom.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.oss2.dasom.auth.provider.OAuthServerProvider.KAKAO;
import static com.oss2.dasom.auth.provider.OAuthServerProvider.NAVER;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NaverMemberResponse(
        String resultcode,
        String message,
        Response response
) {

    public int getAge() {
        String birthYearString = response.birthyear;
        int birthYear = Integer.parseInt(birthYearString);
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - birthYear + 1;
        return age;
    }

    public Gender getGender() {
        String gen = response.gender;
        if ("M".equals(gen)) {
            return Gender.MALE;
        } else if ("F".equals(gen)) {
            return Gender.FEMALE;
        }
        return null;
    }

    public User toDomain() {
        return User.builder()
                .userId(NanoId.makeId())
                .role(Role.ROLE_NOTUSER)
                .oauthId(new OAuthId(String.valueOf(response.id), NAVER))
                .email(response.email)
                .realname(response.name)
                .age(getAge())
                .gender(getGender())
                .build();
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile
    ) {
    }
}