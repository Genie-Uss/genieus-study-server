package shop.genieus.study.domains.stamp.application.repository;

import java.time.LocalDate;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;

public interface StampHistoryRepository {
    StampHistory save(StampHistory stampHistory);

    StampHistory findByUserIdAndVerifiedAt(Long userId, LocalDate verifiedAt);
}
