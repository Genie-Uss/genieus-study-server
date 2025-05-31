package shop.genieus.study.domains.stamp.presentation.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Getter
@Setter
public class StampFilterParams {
  private StampType type;
  private String title;
  private String category;
  private LocalDate startDate;
  private LocalDate endDate;
  private String sortBy = "verifiedAt";
  private String sortDirection = "DESC";
}
