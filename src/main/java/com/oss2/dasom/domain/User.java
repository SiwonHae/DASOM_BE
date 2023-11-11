package com.oss2.dasom.domain;

import com.oss2.dasom.auth.provider.OAuthServerProvider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

    private String username;

    public String nickname;

    public String school;

    private String realname;

    private int age;

    private String email;

    private String univEmail;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Embedded
    private OAuthId oauthId;

}