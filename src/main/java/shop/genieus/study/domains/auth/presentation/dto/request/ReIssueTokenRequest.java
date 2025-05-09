package shop.genieus.study.domains.auth.presentation.dto.request;

import shop.genieus.study.domains.auth.application.dto.info.TokenInfo;

public record ReIssueTokenRequest(String accessToken, String refreshToken) {
  public TokenInfo toInfo() {
    return new TokenInfo(accessToken, refreshToken);
  }
}
