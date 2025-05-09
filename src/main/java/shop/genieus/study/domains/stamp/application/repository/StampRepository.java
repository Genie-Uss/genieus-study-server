package shop.genieus.study.domains.stamp.application.repository;

import shop.genieus.study.domains.stamp.domain.entity.Stamp;

public interface StampRepository {
    Stamp save(Stamp stamp);
}
