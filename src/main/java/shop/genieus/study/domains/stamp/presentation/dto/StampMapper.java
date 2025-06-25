package shop.genieus.study.domains.stamp.presentation.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampSearchInfo;
import shop.genieus.study.domains.stamp.presentation.dto.request.StampFilterParams;

@Component
public class StampMapper {
  public GetStampSearchInfo toInfo(StampFilterParams params, Pageable pageable) {

    GetStampSearchInfo.StampFilterInfo filter =
        new GetStampSearchInfo.StampFilterInfo(
            params.getType(),
            params.getTitle(),
            params.getCategory(),
            params.getStartDate(),
            params.getEndDate(),
            params.getSortBy(),
            params.getSortDirection());
    return new GetStampSearchInfo(filter, pageable);
  }
}
