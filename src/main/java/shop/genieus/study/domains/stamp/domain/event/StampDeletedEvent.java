package shop.genieus.study.domains.stamp.domain.event;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record StampDeletedEvent(Long userId, StampType stampType, LocalDateTime verifiedAt) {
    public static StampDeletedEvent of(Stamp stamp) {
    return new StampDeletedEvent(stamp.getUserId(), stamp.getType(), stamp.getVerifiedAt());
    }
}
