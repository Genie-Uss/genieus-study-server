package shop.genieus.study.domains.stamp.application.repository;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;

public interface StampHistoryRepository {
  StampHistory save(StampHistory stampHistory);

  StampHistory findByUserIdAndVerifiedAt(Long userId, LocalDate verifiedAt);

  List<StampHistory> findByUserIdsAndVerifiedAt(List<Long> userIds, LocalDate verifiedAt);
}
