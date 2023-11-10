package com.oss2.dasom.repository;

import com.oss2.dasom.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);
}
