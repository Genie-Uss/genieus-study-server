package shop.genieus.study.domains.user.domain.vo;

import shop.genieus.study.domains.user.domain.exception.UserValidationException;

public enum UserRole {
  ROLE_USER,
  ROLE_ADMIN;

  public static UserRole from(String value) {
    try {
      return UserRole.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      throw UserValidationException.invalidUserRole(value);
    }
  }

  public boolean isRoleUser() {
    return this == UserRole.ROLE_USER;
  }

  public boolean isRoleAdmin() {
    return this == UserRole.ROLE_ADMIN;
  }
}
