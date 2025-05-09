package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCodingTestStampResult;
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateCodingTestStampResponse(
    Long id,
    StampType type,
    boolean isVerified,
    LocalDateTime verifiedAt,
    AlgorithmType algorithmType,
    PlatformType platformType,
    String description,
    String problemUrl) {
  public static CreateCodingTestStampResponse of(CreateCodingTestStampResult result) {
    return new CreateCodingTestStampResponse(
        result.id(),
        result.type(),
        result.isVerified(),
        result.verifiedAt(),
        result.algorithmType(),
        result.platformType(),
        result.description(),
        result.problemUrl());
  }
}
