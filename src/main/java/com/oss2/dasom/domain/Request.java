package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Request extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String title;
    private String content;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private Result result;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;



}
