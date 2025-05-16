package shop.genieus.study.domains.stamp.presentation.dto.response.create;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateResumeStampResult;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateResumeStampResponse(
    Long id,
    StampType type,
    LocalDateTime verifiedAt,
    String companyName,
    String careerType,
    String activityType,
    String relatedUrl) {
  public static CreateResumeStampResponse of(CreateResumeStampResult result) {
    return new CreateResumeStampResponse(
        result.id(),
        result.type(),
        result.verifiedAt(),
        result.companyName(),
        result.careerType().getFieldName(),
        result.activityType().getFieldName(),
        result.relatedUrl());
  }
}
