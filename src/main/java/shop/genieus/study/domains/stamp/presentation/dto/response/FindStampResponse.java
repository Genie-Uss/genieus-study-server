package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record FindStampResponse(
    Long id,
    StampType type,
    boolean isVerified,
    LocalDateTime verifiedAt
) {
    public static FindStampResponse of(Stamp stamp) {
        return new FindStampResponse(
            stamp.getId(),
            stamp.getType(),
            stamp.getIsVerified(),
            stamp.getVerifiedAt()
        );
    }
}
