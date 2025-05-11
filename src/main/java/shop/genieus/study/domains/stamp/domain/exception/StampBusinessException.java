package shop.genieus.study.domains.stamp.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.BusinessException;
import shop.genieus.study.commons.exception.Domain;

@Getter
public class StampBusinessException extends BusinessException {
  private StampBusinessException(String message) {
    super(message, Domain.STAMP);
  }

  public static StampBusinessException mustCheckInBeforeStamping() {
    return new StampBusinessException("출석 이후 도장을 찍을 수 있습니다.");
  }

  public static StampBusinessException noPermissionForStamp() {
    return new StampBusinessException("해당 도장에 권한이 없습니다.");
  }
}
