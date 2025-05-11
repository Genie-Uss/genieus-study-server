package shop.genieus.study.domains.stamp.presentation.dto.response.create;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCtStampResult;
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateCtStampResponse(
    Long id,
    StampType type,
    LocalDateTime verifiedAt,
    AlgorithmType algorithmType,
    PlatformType platformType,
    String description,
    String problemUrl) {
  public static CreateCtStampResponse of(CreateCtStampResult result) {
    return new CreateCtStampResponse(
        result.id(),
        result.type(),
        result.verifiedAt(),
        result.algorithmType(),
        result.platformType(),
        result.description(),
        result.problemUrl());
  }
}
