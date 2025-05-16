package shop.genieus.study.domains.auth.application.dto.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenFailCode {
  EXPIRE_ACCESS("액세스 토큰이 만료되었습니다."),
  EXPIRE_REFRESH("리프레쉬 토큰이 만료되었습니다."),
  ;

  private final String message;
  private String code = this.name();
}
