package shop.genieus.study.commons.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.context.request.WebRequest;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiErrorResponse {
  private int status;
  private String from;
  @Builder.Default private String timestamp = LocalDateTime.now().toString();
  private String message;
  private Map<String, String> details;
  private String path;

  public static ApiErrorResponse create(Domain domain, Exception exception, int statusCode) {
    return create(domain, exception, statusCode, null, null);
  }

  public static ApiErrorResponse create(
      Domain domain, Exception exception, int statusCode, Object request) {
    return create(domain, exception, statusCode, request, null);
  }

  public static ApiErrorResponse create(
      Domain domain, String message, int statusCode, Object request) {
    return create(domain, message, statusCode, request, null);
  }

  public static ApiErrorResponse create(
      Domain domain,
      Exception exception,
      int statusCode,
      Object request,
      Map<String, String> details) {
    return create(domain, exception.getMessage(), statusCode, request, details);
  }

  public static ApiErrorResponse create(
      Domain domain, String message, int statusCode, Object request, Map<String, String> details) {
    return ApiErrorResponse.builder()
        .from(domain.name())
        .message(message)
        .status(statusCode)
        .path(extractPath(request))
        .details(details)
        .build();
  }

  private static String extractPath(Object request) {
    if (request == null) {
      return null;
    }

    if (request instanceof WebRequest) {
      return ((WebRequest) request).getDescription(false);
    }

    if (request instanceof HttpServletRequest) {
      return ((HttpServletRequest) request).getRequestURI();
    }

    return null;
  }
}
