package shop.genieus.study.domains.attendance.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;

@Getter
@RequiredArgsConstructor
public class AttendanceEvent implements NotificationMessageBuilder {

  private final Long userId;
  private final EventType eventType;

  public static AttendanceEvent checkIn(Long userId) {
    return new AttendanceEvent(userId, EventType.CHECK_IN);
  }

  public static AttendanceEvent checkOut(Long userId) {
    return new AttendanceEvent(userId, EventType.CHECK_OUT);
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

  @Getter(AccessLevel.PRIVATE)
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public enum EventType {
    CHECK_IN("출석 알림", "%s님이 출석했습니다!", "5763719", "🟢"),
    CHECK_OUT("퇴실 알림", "%s님이 퇴실했습니다!", "16743168", "🟠"),
    UNKNOWN("출석 관련 알림", "%s님의 출석 상태가 변경되었습니다.", "10197915", "⚪");

    private final String title;
    private final String message;
    private final String colorCode;
    private final String emoji;
  }
}
