package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDateTime;
import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;

public record TilStampResponse(List<Detail> details) {

  public static TilStampResponse of(List<TilStamp> stamps) {
    return new TilStampResponse(stamps.stream().map(Detail::of).toList());
  }

  public record Detail(
      Long id,
      Long userId,
      LocalDateTime verifiedAt,
      String title, String categoryType, String content, String relatedUrl) {
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
