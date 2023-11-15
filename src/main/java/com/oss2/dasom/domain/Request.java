package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request extends BaseTimeEntity {

    @EmbeddedId
    @AttributeOverride(name ="id",column = @Column(name = "request_id"))
    private NanoId requestId;

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
