package shop.genieus.study.commons.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingUtil {

  public static String maskEmail(String email) {
    if (email == null || email.isBlank() || !email.contains("@")) {
      return "[invalid]";
    }
    String[] parts = email.split("@");
    if (parts[0].length() <= 3) {
      return parts[0].charAt(0) + "***@" + parts[1];
    }
    return parts[0].substring(0, 3) + "***@" + parts[1];
  }

  public static String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  public static String getRequestInfo(HttpServletRequest request) {
    if (request == null) {
      return "[null-request]";
    }

    return String.format(
        "method=%s, path=%s, ip=%s",
        request.getMethod(), request.getRequestURI(), getClientIp(request));
  }
}
