package shop.genieus.study.domains.stamp.application.repository;

import org.springframework.data.domain.Pageable;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampSearchInfo;
import shop.genieus.study.domains.stamp.application.dto.result.GetStampSearchResult;
import shop.genieus.study.domains.stamp.domain.entity.StampView;

public interface StampViewRepository {
  StampView save(StampView view);

  void deleteById(Long id);

  GetStampSearchResult<StampView> findStampViews(
      GetStampSearchInfo.StampFilterInfo info, Pageable pageable);
}
