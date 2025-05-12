package shop.genieus.study.domains.stamp.domain.event;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record StampCreatedEvent(Long userId, StampType stampType, LocalDateTime verifiedAt) {
  public static StampCreatedEvent of(Stamp stamp) {
    return new StampCreatedEvent(stamp.getUserId(), stamp.getType(), stamp.getVerifiedAt());
  }
}
