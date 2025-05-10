package shop.genieus.study.domains.attendance.application.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.NotFoundException;

@Getter
public class AttendanceNotFoundException extends NotFoundException {
  private AttendanceNotFoundException(String message) {
    super(message, Domain.ATTENDANCE);
  }

  public static AttendanceNotFoundException create() {
    return new AttendanceNotFoundException("출석 정보를 찾을 수 없습니다.");
  }
}
