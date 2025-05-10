package shop.genieus.study.domains.stamp.application.repository;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;

public interface StampRepository {
    Stamp save(Stamp stamp);
    List<Stamp> findAllByUserIdAndVerifiedAt(Long userId, LocalDate today);
}
