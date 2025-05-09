package shop.genieus.study.domains.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import shop.genieus.study.domains.auth.application.dto.info.CredentialInfo;

public record LoginRequest(
    @NotBlank(message = "이메일 입력은 필수입니다.") String email,
    @NotBlank(message = "비밀번호 입력은 필수입니다.") String password) {
  public CredentialInfo toInfo() {
    return new CredentialInfo(email, password);
  }
}
