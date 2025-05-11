package shop.genieus.study.domains.stamp.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.NotFoundException;

@Getter
public class StampNotFoundException extends NotFoundException {
  private StampNotFoundException(String message) {
    super(message, Domain.STAMP);
  }

  public static StampNotFoundException of(Long id) {
    return new StampNotFoundException("해당 도장 정보를 찾을 수 없습니다. ID: " + id );
  }
}
