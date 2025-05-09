package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateJobActivityStampResult;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateJobActivityStampResponse(
    Long id,
    StampType type,
    boolean isVerified,
    LocalDateTime verifiedAt,
    String companyName,
    CareerType careerType,
    ActivityType activityType,
    String relatedUrl) {
  public static CreateJobActivityStampResponse of(CreateJobActivityStampResult result) {
    return new CreateJobActivityStampResponse(
        result.id(),
        result.type(),
        result.isVerified(),
        result.verifiedAt(),
        result.companyName(),
        result.careerType(),
        result.activityType(),
        result.relatedUrl());
  }
}
