package shop.genieus.study.commons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
  private final int statusCode = HttpStatus.NOT_FOUND.value();
  private final Domain domain;

  public NotFoundException(String message, Domain domain) {
    super(message);
    this.domain = domain;
  }
}
