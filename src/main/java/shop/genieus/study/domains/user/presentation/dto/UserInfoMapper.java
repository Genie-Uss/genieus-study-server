package shop.genieus.study.domains.user.presentation.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.user.application.dto.info.GetAdminUserSearchInfo;
import shop.genieus.study.domains.user.presentation.dto.request.AdminUserFilterParams;

@Component
public class UserInfoMapper {
  public GetAdminUserSearchInfo toInfo(AdminUserFilterParams params, Pageable pageable) {
    GetAdminUserSearchInfo.AdminUserFilterInfo filter =
        new GetAdminUserSearchInfo.AdminUserFilterInfo(
            params.getStatus(),
            params.getParticipationStatus(),
            params.getNickname(),
            params.getEmail(),
            params.getSortBy(),
            params.getSortDirection());
    return new GetAdminUserSearchInfo(filter, pageable);
  }
}
