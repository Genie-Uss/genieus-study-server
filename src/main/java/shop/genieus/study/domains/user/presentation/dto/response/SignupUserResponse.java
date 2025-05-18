package shop.genieus.study.domains.user.presentation.dto.response;

import shop.genieus.study.domains.user.domain.entity.User;

public record SignupUserResponse(Long id, String email, String nickname) {
  private SignupUserResponse(User user) {
    this(user.getId(), user.getNickname().getValue(), user.getEmail().getValue());
  }

  public static SignupUserResponse from(User user) {
    return new SignupUserResponse(user);
  }
}
