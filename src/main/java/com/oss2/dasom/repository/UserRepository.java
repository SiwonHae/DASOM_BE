package com.oss2.dasom.repository;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserId(NanoId userId);
}
