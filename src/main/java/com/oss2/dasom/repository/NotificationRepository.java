package com.oss2.dasom.repository;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, NanoId> {

    List<Notification> findByRequest_Post_User_UserId(NanoId userId);
}
