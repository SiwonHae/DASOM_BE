package com.oss2.dasom.User;

import com.oss2.dasom.NanoId;
import com.oss2.dasom.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User ,NanoId> {
    Optional<User> findByEmail(String email);
}
