package shop.genieus.study.domains.stamp.application.dto.result;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateCodingTestStampResult(
    Long id,
    StampType type,
    boolean isVerified,
    LocalDateTime verifiedAt,
    AlgorithmType algorithmType,
    PlatformType platformType,
    String description,
    String problemUrl
) {
    public static CreateCodingTestStampResult of(CodingTestStamp stamp) {
        return new CreateCodingTestStampResult(
            stamp.getId(),
            stamp.getType(),
            stamp.getIsVerified(),
            stamp.getVerifiedAt(),
            stamp.getAlgorithmType(),
            stamp.getPlatformType(),
            stamp.getDescription(),
            stamp.getProblemUrl()
        );
    }
}
