package com.oss2.dasom.dto;

import com.oss2.dasom.domain.NotificationKind;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetRequestNotificationResponse(

        String notificationId,
        String requestName,
        String requestContent,
        LocalDateTime requestTime,
        boolean status,
        String postId,
        String postTitle,
        String openKakao,
        NotificationKind kind

) {
}
