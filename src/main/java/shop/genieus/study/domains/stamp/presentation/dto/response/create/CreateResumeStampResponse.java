package shop.genieus.study.domains.stamp.presentation.dto.response.create;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateResumeStampResult;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateResumeStampResponse(
    Long id,
    StampType type,
    LocalDateTime verifiedAt,
    String companyName,
    CareerType careerType,
    ActivityType activityType,
    String relatedUrl) {
  public static CreateResumeStampResponse of(CreateResumeStampResult result) {
    return new CreateResumeStampResponse(
        result.id(),
        result.type(),
        result.verifiedAt(),
        result.companyName(),
        result.careerType(),
        result.activityType(),
        result.relatedUrl());
  }
}
