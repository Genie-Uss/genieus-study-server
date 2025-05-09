package shop.genieus.study.commons.exception;

import lombok.Getter;

@Getter
public class ApiBusinessException extends RuntimeException {
  private final Domain domain;

  public ApiBusinessException(String message, Domain domain) {
    super(message);
    this.domain = domain;
  }
}
