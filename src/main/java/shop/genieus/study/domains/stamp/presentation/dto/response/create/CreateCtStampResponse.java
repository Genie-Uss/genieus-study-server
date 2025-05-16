package shop.genieus.study.domains.stamp.presentation.dto.response.create;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCtStampResult;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateCtStampResponse(
    Long id,
    StampType type,
    LocalDateTime verifiedAt,
    String algorithmType,
    String platformType,
    String description,
    String problemUrl) {
  public static CreateCtStampResponse of(CreateCtStampResult result) {
    return new CreateCtStampResponse(
        result.id(),
        result.type(),
        result.verifiedAt(),
        result.algorithmType().getFieldName(),
        result.platformType().getFieldName(),
        result.description(),
        result.problemUrl());
  }
}
