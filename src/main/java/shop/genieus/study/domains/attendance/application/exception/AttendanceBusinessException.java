package shop.genieus.study.domains.attendance.application.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.BusinessException;
import shop.genieus.study.commons.exception.Domain;

@Getter
public class AttendanceBusinessException extends BusinessException {
  private AttendanceBusinessException(String message) {
    super(message, Domain.ATTENDANCE);
  }

  public static AttendanceBusinessException alreadyCheckedIn() {
    return new AttendanceBusinessException("이미 오늘 출석 처리되었습니다.");
  }
}
