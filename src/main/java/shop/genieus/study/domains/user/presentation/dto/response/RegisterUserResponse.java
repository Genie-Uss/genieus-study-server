package shop.genieus.study.domains.user.presentation.dto.response;

import shop.genieus.study.domains.user.domain.entity.User;

public record RegisterUserResponse(Long id, String email, String nickname) {
  private RegisterUserResponse(User user) {
    this(user.getId(), user.getNickname().getValue(), user.getEmail().getValue());
  }

  public static RegisterUserResponse from(User user) {
    return new RegisterUserResponse(user);
  }
}
