package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;

public record ResumeStampResponse(List<Detail> details) {

  public static ResumeStampResponse mock() {
    return new ResumeStampResponse(
        List.of(
            new Detail(
                100L,
                "테크 주식회사",
                "NEWCOMER",
                "RESUME_WRITING",
                "프론트엔드 개발자 포지션 지원 자기소개서 작성",
                "https://example.com/job/123")));
  }

  public static ResumeStampResponse of(List<ResumeStamp> stamps) {
    return new ResumeStampResponse(stamps.stream().map(Detail::of).toList());
  }

  public record Detail(
      long id,
      String companyName,
      String careerType,
      String activityType,
      String description,
      String relatedUrl) {
    public static Detail of(ResumeStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getCompanyName(),
          stamp.getCareerType().getFieldName(),
          stamp.getActivityType().getFieldName(),
          stamp.getDescription(),
          stamp.getRelatedUrl());
    }
  }
}
