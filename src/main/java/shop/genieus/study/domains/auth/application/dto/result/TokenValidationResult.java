package shop.genieus.study.domains.auth.application.dto.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.genieus.study.domains.auth.domain.vo.TokenId;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenValidationResult {
  private final TokenId tokenId;
  private Long userId;
  private boolean isValid;
  private TokenFailCode failCode;

  public static TokenValidationResult of(String tokenIdValue, Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("사용자 ID는 필수입니다.");
    }
    return new TokenValidationResult(TokenId.of(tokenIdValue), userId, true, null);
  }

  public static TokenValidationResult expiredAccessToken() {
    return new TokenValidationResult(null, null, false, TokenFailCode.EXPIRE_ACCESS);
  }
}
