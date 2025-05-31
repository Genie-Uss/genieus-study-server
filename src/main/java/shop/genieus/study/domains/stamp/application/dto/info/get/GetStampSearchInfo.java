package shop.genieus.study.domains.stamp.application.dto.info.get;

import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import shop.genieus.study.domains.stamp.domain.vo.StampType;
import shop.genieus.study.domains.stamp.presentation.dto.request.StampFilterParams;

public record GetStampSearchInfo(StampFilterInfo filterParams, Pageable pageable) {
  public static GetStampSearchInfo from(StampFilterParams params, Pageable pageable) {

    StampFilterInfo filter =
        new StampFilterInfo(
            params.getType(),
            params.getTitle(),
            params.getCategory(),
            params.getStartDate(),
            params.getEndDate(),
            params.getSortBy(),
            params.getSortDirection());
    return new GetStampSearchInfo(filter, pageable);
  }

  public record StampFilterInfo(
      StampType type,
      String title,
      String category,
      LocalDate startDate,
      LocalDate endDate,
      String sortBy,
      String sortDirection) {}
}
