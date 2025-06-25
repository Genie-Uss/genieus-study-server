package shop.genieus.study.domains.user.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import shop.genieus.study.domains.user.application.dto.info.AdminUserInfo;
import shop.genieus.study.domains.user.application.dto.result.GetAdminUserSearchResult;
import shop.genieus.study.domains.user.application.dto.result.PageInfo;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;

public record AdminUserListResponse(List<UserData> content, PageInfo page) {
  public static AdminUserListResponse from(GetAdminUserSearchResult result) {
    return new AdminUserListResponse(
        result.content().stream().map(data -> UserData.from(data)).toList(), result.page());
  }

  public record UserData(
      Long id,
      String nickname,
      String email,
      String status,
      String participationStatus,
      boolean isParticipating,
      String role,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    public static UserData from(AdminUserInfo info) {
      return new UserData(
          info.id(),
          info.nickname(),
          info.email(),
          info.status(),
          info.participationStatus().name(),
          info.participationStatus() == ParticipationStatus.ACTIVE,
          info.role(),
          info.createdAt(),
          info.updatedAt());
    }
  }
}
