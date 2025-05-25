package shop.genieus.study.domains.user.application.exception;

import java.time.LocalDate;
import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.NotFoundException;

@Getter
public class UserSettingHistoryNotFoundException extends NotFoundException {
  private UserSettingHistoryNotFoundException(String message) {
    super(message, Domain.USER);
  }

  public static UserSettingHistoryNotFoundException create(LocalDate date, Long userId) {
    return new UserSettingHistoryNotFoundException(
        String.format("사용자 ID %d의 %s 날짜에 유효한 설정을 찾을 수 없습니다.", userId, date));
  }

  public static UserSettingHistoryNotFoundException noActiveSettings(Long userId) {
    return new UserSettingHistoryNotFoundException(
        String.format("사용자 ID %d의 활성화된 설정을 찾을 수 없습니다.", userId));
  }
}
