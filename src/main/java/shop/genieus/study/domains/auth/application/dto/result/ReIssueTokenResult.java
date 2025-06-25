package shop.genieus.study.domains.auth.application.dto.result;

import shop.genieus.study.domains.auth.domain.vo.TokenPair;

public record TokenReissueResult(
    TokenPair tokenPair, Long userId, boolean success, TokenFailCode failCode) {
  public static TokenReissueResult of(TokenPair tokenPair, Long userId) {
    return new TokenReissueResult(tokenPair, userId, true, null);
  }

  public static TokenReissueResult expiredRefreshToken() {
    return new TokenReissueResult(null, null, false, TokenFailCode.EXPIRE_REFRESH);
  }
}
