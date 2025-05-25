package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;

public record ResumeStampResponse(LocalDate date, boolean isOwner, List<Detail> details) {

  public static ResumeStampResponse of(
      LocalDate date, CustomPrincipal principal, Long targetUserId, List<ResumeStamp> stamps) {
    return new ResumeStampResponse(
        date, isOwner(principal.id(), targetUserId), stamps.stream().map(Detail::of).toList());
  }

  private static boolean isOwner(Long requestUserId, Long targetUserId) {
    return Objects.equals(requestUserId, targetUserId);
  }

  public record Detail(
      Long id,
      Long userId,
      LocalDateTime verifiedAt,
      String title,
      String careerType,
      String activityType,
      String description,
      String relatedUrl) {
    public static Detail of(ResumeStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getUserId(),
          stamp.getVerifiedAt(),
          stamp.getTitle(),
          stamp.getCareerType().getFieldName(),
          stamp.getActivityType().getFieldName(),
          stamp.getDescription(),
          stamp.getRelatedUrl());
    }
  }
}
