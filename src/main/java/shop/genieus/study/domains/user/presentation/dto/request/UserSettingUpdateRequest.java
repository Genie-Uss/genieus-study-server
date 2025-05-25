package shop.genieus.study.domains.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import shop.genieus.study.domains.user.application.dto.info.UpdateUserSettingInfo;

public record UserSettingUpdateRequest(
    @NotNull(message = "희망 출석 시각은 필수입니다.") @JsonFormat(pattern = "HH:mm")
        LocalTime desiredCheckInTime,
    @NotNull(message = "희망 코어 시간은 필수입니다.") Integer desiredCoreTime) {

  public UpdateUserSettingInfo create(Long requestedUserId, Long targetedUserId) {
    validateOwnership(requestedUserId, targetedUserId);
    return new UpdateUserSettingInfo(targetedUserId, desiredCheckInTime, desiredCoreTime);
  }

  private void validateOwnership(Long requestedUserId, Long targetUserId) {
    if (!Objects.equals(requestedUserId, targetUserId)) {
      throw new AccessDeniedException("본인의 설정만 변경할 수 있습니다.");
    }
  }
}
