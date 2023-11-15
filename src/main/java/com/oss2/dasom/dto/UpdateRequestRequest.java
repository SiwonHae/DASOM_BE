package com.oss2.dasom.dto;

import com.oss2.dasom.domain.NanoId;
import lombok.Getter;

@Getter
public class UpdateRequestRequest {
    private String title;
    private String content;
    private NanoId userId;
}
