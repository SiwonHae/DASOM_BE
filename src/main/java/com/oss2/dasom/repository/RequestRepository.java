package com.oss2.dasom.repository;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.domain.Request;
import com.oss2.dasom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUser(User user);
    List<Request> findByPost(Post post);
    Request findByRequestId(NanoId RequestId);
}
