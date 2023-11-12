package com.oss2.dasom.dto;

import lombok.Builder;

@Builder
public record GetUserResponse(
        String nickname,
        String school
) {
}
