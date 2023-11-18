package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post extends BaseTimeEntity {

    @EmbeddedId
    @AttributeOverride(name ="id",column = @Column(name = "post_id"))
    private NanoId postId;

    private String title;
    private String content;
    private String openKakaoAddress;
    private String alcohol;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    @ManyToOne
    private User user;

    // 미팅 인원
    @Column(name = "number")
    @Enumerated(EnumType.STRING)
    private Number number;

    // 모집 성별(?)
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 달린 신청
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Request> requestList = new ArrayList<>();

    public void addRequest(Request request) {
        requestList.add(request);
        request.setPost(this);
    }
}
