package shop.genieus.study.commons.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorSpec {
  INTERNAL_ERROR(INTERNAL_SERVER_ERROR.value(), "서버 내부 오류로 인해 요청을 처리할 수 없습니다."),
  INVALID_INPUT(BAD_REQUEST.value(), "입력하신 데이터에 오류가 있습니다. 요청 내용을 확인하고 다시 시도해 주세요."),
  NOT_READABLE(BAD_REQUEST.value(), "입력하신 데이터가 잘못된 형식입니다."),
  INVALID_REQUEST(BAD_REQUEST.value(), "요청하신 값이 올바르지 않습니다. 요청 값을 확인해 주세요."),
  NOT_ALLOWED(METHOD_NOT_ALLOWED.value(), "허용되지 않은 HTTP 메서드입니다");

  private final int statusCode;
  private final String message;
}
