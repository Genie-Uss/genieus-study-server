package shop.genieus.study.domains.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import shop.genieus.study.domains.user.application.dto.info.UpdateParticipationStatusInfo;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;

public record UpdateParticipationStatusRequest(
    @NotNull(message = "참여 상태는 필수입니다.") ParticipationStatus participationStatus) {

  public UpdateParticipationStatusInfo toInfo(Long adminUserId, Long targetUserId) {
    return new UpdateParticipationStatusInfo(adminUserId, targetUserId, participationStatus);
  }
}
