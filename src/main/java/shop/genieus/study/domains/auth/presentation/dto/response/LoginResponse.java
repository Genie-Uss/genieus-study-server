package shop.genieus.study.domains.auth.presentation.dto.response;

public record LoginResponse(
    boolean success, String accessToken, String refreshToken, AuthUser user) {

  public static LoginResponse create(
      boolean success,
      String accessToken,
      String refreshToken,
      Long userId,
      String nickname,
      String role) {
    return new LoginResponse(
        success, accessToken, refreshToken, new AuthUser(userId, nickname, role));
  }

  private record AuthUser(Long id, String nickname, String role) {}
}
