package shop.genieus.study.domains.user.application.dto.info;

import org.springframework.data.domain.Pageable;
import shop.genieus.study.domains.user.presentation.dto.request.AdminUserFilterParams;

public record GetAdminUserSearchInfo(AdminUserFilterInfo filterParams, Pageable pageable) {
  public static GetAdminUserSearchInfo from(AdminUserFilterParams params, Pageable pageable) {
    AdminUserFilterInfo filter =
        new AdminUserFilterInfo(
            params.getStatus(),
            params.getParticipationStatus(),
            params.getNickname(),
            params.getEmail(),
            params.getSortBy(),
            params.getSortDirection());
    return new GetAdminUserSearchInfo(filter, pageable);
  }

  public record AdminUserFilterInfo(
      String status,
      String participationStatus,
      String nickname,
      String email,
      String sortBy,
      String sortDirection) {}
}
