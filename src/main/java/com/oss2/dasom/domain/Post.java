package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
