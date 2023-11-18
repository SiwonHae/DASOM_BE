package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "oauth_id_unique",
                        columnNames = {
                                "oauth_server_id",
                                "oauth_server"
                        }
                ),
        }
)
public class User extends BaseTimeEntity {

    @EmbeddedId
    @AttributeOverride(name ="id",column = @Column(name = "user_id"))
    private NanoId userId;

    @Column(unique = true)
    public String nickname;

    public String school;

    private String realname;

    private int age;

    private String email;

    private String univEmail;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private OAuthId oauthId;

    private boolean active;

    private boolean emailCheck;

}