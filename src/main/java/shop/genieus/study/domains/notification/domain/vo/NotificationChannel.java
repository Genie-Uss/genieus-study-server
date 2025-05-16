package shop.genieus.study.domains.notification.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationChannel {
  DISCORD_ATTENDANCE("디스코드-출석"),
  DISCORD_ACTIVITY("디스코드-활동"),
  ;
  private final String description;
}
