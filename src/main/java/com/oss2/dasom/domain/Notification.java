package com.oss2.dasom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseTimeEntity{

    @EmbeddedId
    @AttributeOverride(name ="id",column = @Column(name = "notification_id"))
    private NanoId notificationId;

    @Enumerated(EnumType.STRING)
    private NotificationKind kind;

    private boolean status; // 알림 확인 여부

    @ManyToOne
    @JoinColumn(name = "request_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Request request;

    @Column(name = "receive_user_id")
    private NanoId receiveUserId;
}
