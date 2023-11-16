package com.oss2.dasom.repository;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.domain.Request;
import com.oss2.dasom.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findByUser(User user, Pageable pageable);
    Page<Request> findByPost(Post post, Pageable pageable);
    Request findByRequestId(NanoId RequestId);
}
