package shop.genieus.study.commons.exception;

import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import shop.genieus.study.commons.util.ValidationErrorUtil;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ApiErrorResponse> handleAllExceptions(
      Exception exception, WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.INTERNAL_ERROR;
    ApiErrorResponse response =
        ApiErrorResponse.create(Domain.GLOBAL, spec.getMessage(), spec.getStatusCode(), request);
    return buildResponse(exception, response);
  }

  @ExceptionHandler(ApiBusinessException.class)
  public final ResponseEntity<ApiErrorResponse> handleApiBusinessException(
      ApiBusinessException exception, WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.INTERNAL_ERROR;
    ApiErrorResponse response =
        ApiErrorResponse.create(
            exception.getDomain(), exception.getMessage(), spec.getStatusCode(), request);
    return buildResponse(exception, response);
  }

  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<ApiErrorResponse> handleValidationException(
      ValidationException exception, WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.INVALID_INPUT;
    ApiErrorResponse response =
        ApiErrorResponse.create(
            exception.getDomain(), exception.getMessage(), spec.getStatusCode(), request);
    return buildResponse(exception, response);
  }

  @ExceptionHandler(BusinessException.class)
  public final ResponseEntity<ApiErrorResponse> handleBusinessException(
      BusinessException exception, WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.INVALID_REQUEST;
    ApiErrorResponse response =
        ApiErrorResponse.create(
            exception.getDomain(), exception.getMessage(), spec.getStatusCode(), request);
    return buildResponse(exception, response);
  }

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ApiErrorResponse> handleNotFoundException(
      NotFoundException exception, WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.NOT_FOUND;
    ApiErrorResponse response =
        ApiErrorResponse.create(
            exception.getDomain(), exception.getMessage(), spec.getStatusCode(), request);
    return buildResponse(exception, response);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.INVALID_INPUT;

    ApiErrorResponse response =
        ApiErrorResponse.create(
            Domain.GLOBAL,
            exception.getMessage(),
            spec.getStatusCode(),
            request,
            ValidationErrorUtil.extractFieldErrors(exception));
    return buildResponse(exception, response);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.NOT_READABLE;
    Map<String, String> details = Map.of("message", exception.getMessage());
    ApiErrorResponse response =
        ApiErrorResponse.create(
            Domain.GLOBAL, exception.getMessage(), spec.getStatusCode(), request, details);
    return buildResponse(exception, response);
  }

  @Override
  protected ResponseEntity<Object> handleHandlerMethodValidationException(
      HandlerMethodValidationException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    GlobalErrorSpec spec = GlobalErrorSpec.INVALID_REQUEST;
    Map<String, String> details = ValidationErrorUtil.extractMethodErrors(exception);
    ApiErrorResponse response =
        ApiErrorResponse.create(
            Domain.GLOBAL, exception.getMessage(), spec.getStatusCode(), request, details);
    return buildResponse(exception, response);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    GlobalErrorSpec spec = GlobalErrorSpec.NOT_ALLOWED;
    Map<String, String> details = Map.of("method", exception.getMethod());
    ApiErrorResponse response =
        ApiErrorResponse.create(
            Domain.GLOBAL, spec.getMessage(), spec.getStatusCode(), request, details);
    return buildResponse(exception, response);
  }

  private <T> ResponseEntity<T> buildResponse(Exception exception, ApiErrorResponse response) {
    logErrorIfEnabled(exception, response, true);
    return new ResponseEntity<>((T) response, HttpStatusCode.valueOf(response.getStatus()));
  }

  private void logErrorIfEnabled(
      Exception exception, ApiErrorResponse response, boolean shouldLog) {
    if (shouldLog) {
      log.error(
          "Exception: system message: {}, response body: {}", exception.getMessage(), response);
    }
  }
}
