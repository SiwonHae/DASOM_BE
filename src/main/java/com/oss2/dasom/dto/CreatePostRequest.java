package com.oss2.dasom.dto;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Number;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title;
    private String content;
    private String openKakaoAddress;
    private String alcohol;
    private NanoId userId;
    private Number number;
    private Gender gender;
}
