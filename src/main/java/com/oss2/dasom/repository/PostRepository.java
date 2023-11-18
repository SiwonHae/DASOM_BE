package com.oss2.dasom.repository;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.domain.Number;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(NanoId postId);

    Page<Post> findAllByActive(boolean active, Pageable pageable);

    Optional<Page<Post>> findByUser(User user, Pageable pageable);

    Optional<List<Post>> findByUser(User user);

    Optional<Page<Post>> findByGenderAndActive(Gender gender, boolean active, Pageable pageable);

    Optional<Page<Post>> findByNumberAndActive(Number number, boolean active, Pageable pageable);

    Optional<Page<Post>> findByGenderAndNumberAndActive(Gender gender, Number number, boolean active, Pageable pageable);

    @Query("SELECT e FROM Post e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Optional<Page<Post>> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
