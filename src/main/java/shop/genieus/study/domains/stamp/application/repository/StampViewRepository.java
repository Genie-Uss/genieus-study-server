package shop.genieus.study.domains.stamp.application.repository;

import shop.genieus.study.domains.stamp.domain.entity.StampView;

public interface StampViewRepository {
  StampView save(StampView view);

  void deleteById(Long id);
}
