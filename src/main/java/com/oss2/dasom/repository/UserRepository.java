package com.oss2.dasom.repository;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.OAuthId;
import com.oss2.dasom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, NanoId> {
    Optional<User> findByOauthId(OAuthId oauthId);
    Optional<User> findByUserId(NanoId userId);
    Optional<User> findByUserIdAndActiveTrue(NanoId userId);
}
