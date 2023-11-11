package com.oss2.dasom.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oss2.dasom.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.oss2.dasom.auth.provider.OAuthServerProvider.KAKAO;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMemberResponse(
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    public int getAge() {
        String birthYearString = kakaoAccount().birthyear;
        int birthYear = Integer.parseInt(birthYearString);
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - birthYear + 1;
        return age;
    }

    public Gender getGender() {
        String gen = kakaoAccount.gender();
        if ("male".equals(gen)) {
            return Gender.MALE;
        } else if ("female".equals(gen)) {
            return Gender.FEMALE;
        }
        return null;
    }

    public User toDomain() {
        return User.builder()
                .userId(NanoId.makeId())
                .role(Role.ROLE_NOTUSER)
                .oauthId(new OAuthId(String.valueOf(id), KAKAO))
                .email(kakaoAccount().email)
                .realname(kakaoAccount().name)
                .age(getAge())
                .gender(getGender())
                .build();
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            Profile profile,
            boolean nameNeedsAgreement,
            String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean birthyearNeedsAgreement,
            String birthyear,
            boolean birthdayNeedsAgreement,
            String birthday,
            String birthdayType,
            boolean genderNeedsAgreement,
            String gender,
            boolean phoneNumberNeedsAgreement,
            String phoneNumber,
            boolean ciNeedsAgreement,
            String ci,
            LocalDateTime ciAuthenticatedAt
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {
    }
}