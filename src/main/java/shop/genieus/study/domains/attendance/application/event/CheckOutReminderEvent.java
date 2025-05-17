package shop.genieus.study.domains.attendance.application.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;

@Getter
@RequiredArgsConstructor
public class CheckOutReminderEvent implements NotificationMessageBuilder {

  private final Long userId = -1L;
  private final String serverUrl;

  @Override
  public String buildTitle() {
    return "**[코어 시간 종료] 오늘도 수고하셨습니다!**";
  }

  @Override
  public String buildMessage() {
    return "코어 시간이 종료되었습니다. "
        + " 수고 많으셨어요🎉 "
        + "남은 하루도 좋은 하루 되세요! 🌟\n\n"
        + "　　:link: [퇴실 체크하러 가기]("
        + serverUrl
        + ")";
  }

  @Override
  public String getColorCode() {
    return "15105570";
  }

  @Override
  public NotificationChannelType getChannelType() {
    return NotificationChannelType.ATTENDANCE;
  }

  @Override
  public String getEmoji() {
    return "🏁 ";
  }
}
