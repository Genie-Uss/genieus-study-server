package shop.genieus.study.domains.auth.application.dto.result;

import shop.genieus.study.domains.auth.domain.vo.TokenPair;

public record ReIssueTokenResult(
    TokenPair tokenPair, Long userId, boolean success, TokenFailCode failCode) {
  public static ReIssueTokenResult of(TokenPair tokenPair, Long userId) {
    return new ReIssueTokenResult(tokenPair, userId, true, null);
  }

  public static ReIssueTokenResult expiredRefreshToken() {
    return new ReIssueTokenResult(null, null, false, TokenFailCode.EXPIRE_REFRESH);
  }
}
