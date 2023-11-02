package com.oss2.dasom.User.dto;

import com.oss2.dasom.Gender;

public record SignUpRequest(
        String email,
        String password,
        String school,
        String nickname,
        Gender gender
) {
}
