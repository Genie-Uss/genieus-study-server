package shop.genieus.study.domains.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import shop.genieus.study.domains.user.application.dto.info.ApproveUserInfo;

public record ApproveUserRequest(
    @NotNull(message = "승인 여부는 필수입니다.") Boolean isApproved,
    @NotNull(message = "참여 여부는 필수입니다.") Boolean isParticipating) {

  public ApproveUserInfo toInfo(Long adminUserId, Long targetUserId) {
    return new ApproveUserInfo(adminUserId, targetUserId, isApproved, isParticipating);
  }
}
