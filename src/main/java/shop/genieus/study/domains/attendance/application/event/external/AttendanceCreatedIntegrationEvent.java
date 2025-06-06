package shop.genieus.study.domains.attendance.application.event.external;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.event.DomainEvent;
import shop.genieus.study.commons.event.DomainEventType;
import shop.genieus.study.commons.event.DomainPayload;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

@Getter
@RequiredArgsConstructor
public class AttendanceCreatedIntegrationEvent implements NotificationMessageBuilder, DomainEvent {

  private final Long userId;
  private final EventType eventType;
  private final DomainEventType domainEventType;

  public static AttendanceCreatedIntegrationEvent checkIn(Attendance attendance) {
    return new AttendanceCreatedIntegrationEvent(
        attendance.getId(), EventType.CHECK_IN, DomainEventType.CHECK_IN);
  }

  public static AttendanceCreatedIntegrationEvent checkOut(Attendance attendance) {
    return new AttendanceCreatedIntegrationEvent(
        attendance.getId(), EventType.CHECK_OUT, DomainEventType.CHECK_OUT);
  }

  @Override
  public String buildTitle() {
    return eventType.getTitle();
  }

  @Override
  public String buildMessage() {
    return eventType.getMessage();
  }

  @Override
  public String getColorCode() {
    return eventType.getColorCode();
  }

  @Override
  public NotificationChannelType getChannelType() {
    return NotificationChannelType.ATTENDANCE;
  }

  @Override
  public String getEmoji() {
    return eventType.getEmoji();
  }

  @Override
  public DomainPayload getDomainPayload() {
    return DomainPayload.builder().userId(this.userId).build();
  }

  @Getter(AccessLevel.PRIVATE)
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public enum EventType {
    CHECK_IN("출석 알림", "%s님이 출석했습니다!", "5763719", "🟢"),
    CHECK_OUT("퇴실 알림", "%s님이 퇴실했습니다!", "16743168", "🟠"),
    ;

    private final String title;
    private final String message;
    private final String colorCode;
    private final String emoji;
  }
}
