package shop.genieus.study.domains.user.presentation.dto.response;

import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.user.application.dto.result.UserInfoResult;

public record UserInfoResponse(Long userId, String nickname, boolean isOwner) {
  public static UserInfoResponse from(UserInfoResult result, boolean isOwner) {
    return new UserInfoResponse(result.userId(), result.nickname(), isOwner);
  }

  public static UserInfoResponse from(CustomPrincipal principal, boolean isOwner) {
    return new UserInfoResponse(principal.id(), principal.nickname(), isOwner);
  }
}
