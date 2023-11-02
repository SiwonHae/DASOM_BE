package com.oss2.dasom.User;

import com.oss2.dasom.config.BaseTimeEntity;
import com.oss2.dasom.Gender;
import com.oss2.dasom.NanoId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @EmbeddedId
    @AttributeOverride(name ="id",column = @Column(name = "user_id"))
    private NanoId userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "school", nullable = false)
    private String school;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


}
