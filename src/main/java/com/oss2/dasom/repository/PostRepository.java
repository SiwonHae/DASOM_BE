package com.oss2.dasom.repository;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(NanoId postId);

    Optional<List<Post>> findByUserId(NanoId userId);

    Optional<List<Post>> findByGender(Gender gender);

    Page<Post> findAllOrderByCreatedDateDesc(PageRequest pageRequest);
}
