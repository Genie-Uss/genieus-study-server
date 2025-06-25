package shop.genieus.study.domains.stamp.application.dto.info.get;

import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record GetStampSearchInfo(StampFilterInfo filterParams, Pageable pageable) {
  public record StampFilterInfo(
      StampType type,
      String title,
      String category,
      LocalDate startDate,
      LocalDate endDate,
      String sortBy,
      String sortDirection) {}
}
