package shop.genieus.study.domains.auth.application.util;

import java.time.Instant;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.domain.vo.TokenPair;

public interface TokenUtils {
  TokenPair createTokenPair(String tokenIdValue, Long subject);

  Instant getExpirationTime(String accessToken);

  TokenValidationResult validateTokenAndExtractId(String token);
}
