package com.oss2.dasom.repository;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(NanoId postId);

    Page<Post> findAllOrderByCreatedDateDesc(PageRequest pageRequest);
}
