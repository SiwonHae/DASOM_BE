package com.oss2.dasom.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {
    String nickname;
    String school;
    String univEmail;
    int inputVerifyCode;
}
