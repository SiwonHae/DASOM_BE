package com.oss2.dasom.controller;

import com.oss2.dasom.dto.GetRequestNotificationResponse;
import com.oss2.dasom.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable String userId) {
        List<GetRequestNotificationResponse> getRequestNotificationResponse = notificationService.sendRequestNotification(userId);
        return ResponseEntity.ok().body(getRequestNotificationResponse);
    }
}
