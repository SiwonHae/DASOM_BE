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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String openKakaoAddress;
    private String alcohol;

    @ManyToOne
    private User user;

    // 미팅 인원
    @Column(name = "number", nullable = false)
    @Enumerated(EnumType.STRING)
    private Number number;

    // 모집 성별(?)
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
