package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ResumeStampResponse(LocalDate date, List<Detail> details) {

  public static ResumeStampResponse mock() {
    return new ResumeStampResponse(
        LocalDate.of(2025, 5, 11),
        List.of(
            new Detail(
                100L,
                "테크 주식회사",
                "NEWCOMER",
                "RESUME_WRITING",
                "프론트엔드 개발자 포지션 지원 자기소개서 작성",
                "https://example.com/job/123")));
  }

  public record Detail(
      long id,
      String companyName,
      String careerType,
      String activityType,
      String description,
      String relatedUrl) {}
}
