package com.oss2.dasom.repository;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(NanoId postId);

    Page<Post> findAllByActive(boolean active, Pageable pageable);

    Optional<Page<Post>> findByUser(User user, Pageable pageable);

    Optional<Page<Post>> findByGenderAndActive(Gender gender, boolean active, Pageable pageable);
}
