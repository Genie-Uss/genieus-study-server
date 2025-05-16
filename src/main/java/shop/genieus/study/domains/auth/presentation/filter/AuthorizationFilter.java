package shop.genieus.study.domains.auth.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.genieus.study.domains.auth.application.AuthorizationService;
import shop.genieus.study.domains.auth.application.dto.result.TokenFailCode;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.auth.presentation.utils.AuthResponseSender;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  private final AuthorizationService authorizationService;
  private final AuthResponseSender authResponseSender;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return path.equals("/users") && request.getMethod().equals("POST") || path.equals("/errors");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
    try {
      String token = extractTokenFromHeader(request);

      if (token != null) {
        TokenValidationResult result = authorizationService.validateAccessToken(token);
        if (!result.isValid()) {
          TokenFailCode failCode = result.getFailCode();
          Map<String, String> details = new HashMap<>(Map.of("code", failCode.getCode()));
          handleAuthorizationFailure(request, response, failCode.getMessage(), details);
          return;
        }
        setAuthentication(result.getUserId());
      } else {
        String message = "유효하지 않은 토큰 값입니다.";
        log.warn("원인: {}, 토큰: {}", message, token);
        handleAuthorizationFailure(request, response, message, null);
        return;
      }

      filterChain.doFilter(request, response);
    } catch (AccessDeniedException accessDeniedException) {
      log.error("AuthenticationException 오류: ", accessDeniedException);
      handleAuthorizationFailure(request, response, accessDeniedException.getMessage(), null);
    } catch (Exception e) {
      log.error("접근 권한 확인 중 오류 발생: ", e);
      handleAuthorizationFailure(request, response, e.getMessage(), null);
    }
  }

  private String extractTokenFromHeader(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTHORIZATION_HEADER);
    if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
      return authHeader.substring(BEARER_PREFIX.length());
    }
    return null;
  }

  private void handleAuthorizationFailure(
      HttpServletRequest request,
      HttpServletResponse response,
      String message,
      Map<String, String> details) {
    authResponseSender.sendErrorResponse(
        request, response, HttpServletResponse.SC_FORBIDDEN, message, details);
  }

  public void setAuthentication(Long useId) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(useId);
    if (authentication == null) {
      throw new AccessDeniedException("잘못된 컨텍스트 저장입니다.");
    }
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
  }

  private Authentication createAuthentication(Long userId) {
    CustomPrincipal principal = authorizationService.getPrincipal(userId);
    return new UsernamePasswordAuthenticationToken(
        principal, null, extractAuthorities(principal.role()));
  }

  private List<GrantedAuthority> extractAuthorities(String role) {
    return List.of(new SimpleGrantedAuthority(role));
  }
}
