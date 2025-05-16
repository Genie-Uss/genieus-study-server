package shop.genieus.study.domains.notification.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.Comment;
import shop.genieus.study.commons.jpa.BaseEntity;
import shop.genieus.study.domains.notification.domain.vo.NotificationChannel;

@Entity
@Getter
@Comment("알림 테이블")
@Table(name = "g_notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

  @Id
  @Comment("알림 아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("사용자 ID")
  private Long userId;

  @Comment("알림 제목")
  @Column(nullable = false)
  private String title;

  @Comment("알림 내용")
  @Column(nullable = false, columnDefinition = "TEXT")
  private String message;

  @Comment("알림 채널")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NotificationChannel channel;

  @Comment("발송 시간")
  private LocalDateTime sentAt;

  @Comment("발송 성공 여부")
  private boolean isSuccess;

  @Comment("에러 메시지")
  private String errorMessage;

  @Builder(access = AccessLevel.PRIVATE)
  private Notification(Long userId, String title, String message, NotificationChannel channel) {
    this.userId = userId;
    this.title = title;
    this.message = message;
    this.channel = channel;
    this.isSuccess = false;
  }

  public static Notification create(
      Long userId, String title, String message, NotificationChannel channel) {
    return Notification.builder()
        .userId(userId)
        .title(title)
        .message(message)
        .channel(channel)
        .build();
  }

  public void markAsSent() {
    this.sentAt = LocalDateTime.now();
    this.isSuccess = true;
  }

  public void markAsFailed(String errorMessage) {
    this.sentAt = LocalDateTime.now();
    this.isSuccess = false;
    this.errorMessage = errorMessage;
  }
}
