package shop.genieus.study.commons.exception;

import org.springframework.http.HttpStatusCode;

public interface ErrorSpec {
  HttpStatusCode getStatus();

  String getMessage();

  String getCode();
}
