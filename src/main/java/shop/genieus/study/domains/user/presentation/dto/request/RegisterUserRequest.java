package shop.genieus.study.domains.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import shop.genieus.study.domains.user.application.dto.info.RegisterUserInfo;

public record RegisterUserRequest(
    @NotBlank(message = "이메일 주소를 입력해 주세요") @Email(message = "이메일 형식이 아닙니다") String email,
    @NotBlank(message = "비밀번호를 입력해 주세요") String password,
    @NotBlank(message = "비밀번호 확인이 입력되지 않았습니다") String confirmPassword,
    @NotBlank(message = "닉네임이 입력되지 않았습니다.") String nickname) {
  public RegisterUserInfo to() {
    return new RegisterUserInfo(this.email, this.password, this.confirmPassword, this.nickname);
  }
}
