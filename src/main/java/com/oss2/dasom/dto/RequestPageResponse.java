package com.oss2.dasom.dto;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Result;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestPageResponse {
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Result result;
    private String nickname;
    private NanoId requestId;
    private NanoId userId;
    private NanoId postId;
}
