package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDateTime;
import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;

public record ResumeStampResponse(List<Detail> details) {

  public static ResumeStampResponse of(List<ResumeStamp> stamps) {
    return new ResumeStampResponse(stamps.stream().map(Detail::of).toList());
  }

  public record Detail(
      Long id,
      Long userId,
      LocalDateTime verifiedAt,
      String companyName,
      String careerType,
      String activityType,
      String description,
      String relatedUrl) {
    public static Detail of(ResumeStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getUserId(),
          stamp.getVerifiedAt(),
          stamp.getCompanyName(),
          stamp.getCareerType().getFieldName(),
          stamp.getActivityType().getFieldName(),
          stamp.getDescription(),
          stamp.getRelatedUrl());
    }
  }
}
