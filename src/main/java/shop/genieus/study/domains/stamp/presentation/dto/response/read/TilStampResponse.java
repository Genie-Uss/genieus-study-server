package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;

public record TilStampResponse(LocalDate date, boolean isOwner, List<Detail> details) {

  public static TilStampResponse of(
      LocalDate date, CustomPrincipal principal, Long targetUserId, List<TilStamp> stamps) {
    return new TilStampResponse(
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
      String categoryType,
      String content,
      String relatedUrl) {
    public static Detail of(TilStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getUserId(),
          stamp.getVerifiedAt(),
          stamp.getTitle(),
          stamp.getCategoryType().getFieldName(),
          stamp.getContent(),
          stamp.getRelatedUrl());
    }
  }
}
