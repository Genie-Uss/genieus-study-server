package shop.genieus.study.domains.auth.domain.exception;

import org.springframework.security.access.AccessDeniedException;

public class CustomAccessDeniedException extends AccessDeniedException {
  private CustomAccessDeniedException(String message) {
    super(message);
  }
}
