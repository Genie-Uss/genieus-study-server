package shop.genieus.study.domains.attendance.application.event.external;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.event.DomainEvent;
import shop.genieus.study.commons.event.DomainEventType;
import shop.genieus.study.commons.event.DomainPayload;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;

@Getter
@RequiredArgsConstructor
public class CheckInReminderEvent implements NotificationMessageBuilder, DomainEvent {

  private final Long userId = -1L;
  private final String serverUrl;

  @Override
  public String buildTitle() {
    return "**[출석 알림] 출석 시간입니다!**";
  }

  @Override
  public String buildMessage() {
    String sb =
        "여러분 출석해주세요! 출석 시간입니다🌞 "
            + "오늘도 화이팅하세요! 😊\n\n"
            + "　　_:link: [출석 체크하러 가기]("
            + serverUrl
            + ")_";

    return sb;
  }

  @Override
  public String getColorCode() {
    return "3447003";
  }

  @Override
  public NotificationChannelType getChannelType() {
    return NotificationChannelType.ATTENDANCE;
  }

  @Override
  public String getEmoji() {
    return "⏰ ";
  }

  @Override
  public DomainEventType getDomainEventType() {
    return DomainEventType.REMIND_CHECK_IN;
  }

  @Override
  public DomainPayload getDomainPayload() {
    return DomainPayload.builder().userId(this.userId).build();
  }
}
