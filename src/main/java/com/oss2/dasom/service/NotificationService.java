package com.oss2.dasom.service;

import com.oss2.dasom.domain.*;
import com.oss2.dasom.dto.GetRequestNotificationResponse;
import com.oss2.dasom.repository.NotificationRepository;
import com.oss2.dasom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public void saveRequest(Request request, NanoId receiveUserId, NotificationKind kind) {
        notificationRepository.save(Notification.builder()
                .notificationId(NanoId.makeId())
                .kind(kind)
                .request(request)
                .receiveUserId(receiveUserId) // 알림을 받을 userId
                .status(false)
                .build());
    }

    @Transactional
    public List<GetRequestNotificationResponse> sendRequestNotification(String userId) {
        User user = userRepository.findByUserIdAndActiveTrue(NanoId.of(userId)).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        List<Notification> notifications = notificationRepository.findByReceiveUserId(user.getUserId());
        
        List<GetRequestNotificationResponse> getRequestNotificationResponses = notifications.stream().map(noti -> {
            Request request = noti.getRequest(); // 신청 정보

            String receiveName = (noti.getKind() == NotificationKind.YES) ? request.getUser().getNickname() : request.getPost().getUser().getNickname();

            if (noti.getKind() == NotificationKind.YES) {
                return GetRequestNotificationResponse.builder()
                        .notificationId(noti.getNotificationId().toString())
                        .requestName(receiveName) // 신청한 사람 이름
                        .requestContent(request.getContent())
                        .requestTime(noti.getCreatedDate())
                        .status(noti.isStatus())
                        .postId(String.valueOf(request.getPost().getPostId()))
                        .postTitle(request.getPost().getTitle())
                        .openKakao(request.getPost().getOpenKakaoAddress())
                        .kind(noti.getKind())
                        .build();
            } else { // 알림 종류가 REQUEST와 NO 일 때 보냄.
                return GetRequestNotificationResponse.builder()
                        .notificationId(noti.getNotificationId().toString())
                        .requestName(receiveName)
                        .requestContent(request.getContent())
                        .requestTime(noti.getCreatedDate())
                        .status(noti.isStatus())
                        .postId(String.valueOf(request.getPost().getPostId()))
                        .postTitle(request.getPost().getTitle())
                        .kind(noti.getKind())
                        .build();
            }
        }).toList();
        notifications.forEach(n -> n.setStatus(true));
        return getRequestNotificationResponses;
    }

}
