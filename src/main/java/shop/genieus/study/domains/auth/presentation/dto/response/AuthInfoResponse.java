package shop.genieus.study.domains.auth.presentation.dto.response;

import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

public record AuthInfoResponse(Long id, String nickname, String role) {
  public static AuthInfoResponse from(CustomPrincipal principal) {
    return new AuthInfoResponse(principal.id(), principal.nickname(), principal.role());
  }
}
