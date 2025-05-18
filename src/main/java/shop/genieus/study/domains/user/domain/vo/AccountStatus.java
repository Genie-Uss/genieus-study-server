package shop.genieus.study.domains.user.domain.vo;

public enum AccountStatus {
  PENDING,
  APPROVED,
  REJECTED,
  INACTIVE;

  public boolean canLogin() {
    return this == APPROVED;
  }

  public boolean isPending() {
    return this == PENDING;
  }

  public boolean isRejected() {
    return this == REJECTED;
  }

  public boolean isInactive() {
    return this == INACTIVE;
  }

  public boolean isLocked() {
    return this == PENDING || this == REJECTED || this == INACTIVE;
  }
}
