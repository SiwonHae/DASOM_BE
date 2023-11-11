package com.oss2.dasom.dto;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Request;
import com.oss2.dasom.domain.Result;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetRequestResponse {
    private Long requestId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Result result;
    private NanoId userId;
    private Long postId;

    public GetRequestResponse(Request request) {
        this.requestId = request.getRequestId();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.createdDate = request.getCreatedDate();
        this.modifiedDate = request.getModifiedDate();
        this.result = request.getResult();
        this.userId = request.getUser().getUserId();
        this.postId = request.getPost().getPostId();
    }

}
