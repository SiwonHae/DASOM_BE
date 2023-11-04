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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Setter
    public String nickname;

    @Setter
    public String school;

    private String realname;

    private int age;

    @Setter
    private String email;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    private Gender gender;

    private String provider;
    private String providerId;

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String username, Gender gender, String school, String nickname, String realname, int age, String email, Role role, String provider, String providerId) {
        this.username = username;
        this.nickname = nickname;
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