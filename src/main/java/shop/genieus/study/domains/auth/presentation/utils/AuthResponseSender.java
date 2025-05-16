package shop.genieus.study.domains.auth.presentation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.exception.ApiErrorResponse;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.util.LoggingUtil;
import shop.genieus.study.domains.auth.domain.exception.AuthServiceException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthResponseSender {
  private final ObjectMapper objectMapper;

  public void sendSuccessResponse(HttpServletResponse response, Object body) {
    try {
      configureResponse(response, HttpServletResponse.SC_OK);
      response.getWriter().write(objectMapper.writeValueAsString(body));
    } catch (IOException e) {
      log.error("응답 전송 중 IO 오류: {}", e.getMessage());
      throw new RuntimeException("응답 전송 중 오류 발생", e);
    }
  }

  public void sendErrorResponse(
      HttpServletRequest request,
      HttpServletResponse response,
      int statusCode,
      String message,
      Map<String, String> details) {
    try {
      configureResponse(response, statusCode);
      writeErrorResponse(request, response, statusCode, message, details);

      if (log.isDebugEnabled()) {
        log.debug(
            "에러 응답 전송 - 상태: {}, 메시지: {}, 요청: {}",
            statusCode,
            message,
            LoggingUtil.getRequestInfo(request));
      }
    } catch (IOException e) {
      log.error("에러 응답 전송 중 IO 오류: {}", e.getMessage());
      throw new AuthServiceException(e.getMessage());
    }
  }

  private void configureResponse(HttpServletResponse response, int statusCode) {
    response.setStatus(statusCode);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
  }

  private void writeErrorResponse(
      HttpServletRequest request,
      HttpServletResponse response,
      int statusCode,
      String message,
      Map<String, String> details)
      throws IOException {
    ApiErrorResponse errorResponse =
        ApiErrorResponse.create(Domain.AUTH, message, statusCode, request, details);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
