package shop.genieus.study.domains.user.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserFilterParams {
  private String status;
  private String participationStatus;
  private String nickname;
  private String email;
  private String sortBy = "createdAt";
  private String sortDirection = "DESC";
}
