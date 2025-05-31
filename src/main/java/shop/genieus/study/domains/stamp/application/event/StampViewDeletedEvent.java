package shop.genieus.study.domains.stamp.application.event;

import shop.genieus.study.domains.stamp.domain.vo.*;

public record StampViewDeletedEvent(Long id) {
  public static StampViewDeletedEvent of(Long stampId) {
    return new StampViewDeletedEvent(stampId);
  }
}
