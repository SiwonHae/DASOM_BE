package com.oss2.dasom.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpRequest {
    String userId;
    String nickname;
    String school;
    String univemail;
    int inputVerifyCode;
}
