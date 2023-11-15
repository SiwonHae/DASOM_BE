package com.oss2.dasom.dto;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Number;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PageResponse {
    private String title;
    private LocalDateTime createdDate;
    private String nickname;
    private Number number;
    private Gender gender;
    private NanoId postId;
}
