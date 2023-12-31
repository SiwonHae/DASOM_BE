package com.oss2.dasom.dto;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Number;
import com.oss2.dasom.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
public class GetPostResponse {
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String openKakaoAddress;
    private String alcohol;
    private NanoId userId;
    private String nickname;
    private Number number;
    private Gender gender;
    private NanoId postId;


    public GetPostResponse(Post post) { // post 객체 받아서 GetPostResponse 객체 생성
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.openKakaoAddress = post.getOpenKakaoAddress();
        this.alcohol = post.getAlcohol();
        this.nickname = post.getUser().getNickname();
        this.number = post.getNumber();
        this.gender = post.getGender();
        this.userId = post.getUser().getUserId();
        this.postId = post.getPostId();
    }

}
