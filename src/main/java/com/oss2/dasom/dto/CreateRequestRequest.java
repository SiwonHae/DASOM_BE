package com.oss2.dasom.dto;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Result;
import lombok.Getter;

@Getter
public class CreateRequestRequest {
    private String title;
    private String content;
    private NanoId userId;
    private NanoId postId;
}
