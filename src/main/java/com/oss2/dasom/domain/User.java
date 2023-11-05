package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Setter
    @EmbeddedId
    @AttributeOverride(name ="id",column = @Column(name = "user_id"))
    private NanoId userId;

    private String username;

    @Setter
    public String nickname;

    @Setter
    public String school;

    private String realname;

    private int age;

    @Setter
    private String email;

    private String univEmail;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String provider;
    private String providerId;

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String username, String univEmail, Gender gender, String school, String nickname, String realname, int age, String email, Role role, String provider, String providerId) {
        this.username = username;
        this.nickname = nickname;
        this.univEmail = univEmail;
        this.school = school;
        this.email = email;
        this.realname = realname;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}