package com.oss2.dasom.domain;

public enum NotificationKind {
    REQUEST, YES, NO

    // REQUEST : 글을 올린 사람에게 신청자가 있다고 보내는 알림.
    // YES : 신청자에게 수락했다고 보내는 알림.
    // NO : 신청자에게 거절했다고 보내는 알림.
}
