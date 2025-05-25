package shop.genieus.study.domains.user.presentation.dto.response;

import shop.genieus.study.commons.provider.dto.UserSettingHistoryInfo;

public record UserSettingHistoryResponse(
    Long id,
    Long userId,
    String effectiveFromDate,
    String effectiveToDate,
    String desiredCheckInTime,
    int desiredCoreTime,
    boolean isActive) {
  public static UserSettingHistoryResponse from(UserSettingHistoryInfo info) {
    return new UserSettingHistoryResponse(
        info.id(),
        info.userId(),
        info.effectiveFromDate().toString(),
        info.effectiveToDate() != null ? info.effectiveToDate().toString() : null,
        info.desiredCheckInTime().toString(),
        info.desiredCoreTime(),
        info.isActive());
  }
}
