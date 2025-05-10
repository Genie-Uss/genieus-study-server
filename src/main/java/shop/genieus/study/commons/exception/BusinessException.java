package shop.genieus.study.commons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
  private final int statusCode = HttpStatus.BAD_REQUEST.value();
  private final Domain domain;

  public BusinessException(String message, Domain domain) {
    super(message);
    this.domain = domain;
  }
}
