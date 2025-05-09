package shop.genieus.study.domains.stamp.application.dto.result;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateTilStampResult(
    Long id,
    StampType type,
    boolean isVerified,
    LocalDateTime verifiedAt,
    String title,
    CategoryType categoryType,
    String content,
    String relatedUrl) {
  public static CreateTilStampResult of(TilStamp stamp) {
    return new CreateTilStampResult(
        stamp.getId(),
        stamp.getType(),
        stamp.getIsVerified(),
        stamp.getVerifiedAt(),
        stamp.getTitle(),
        stamp.getCategoryType(),
        stamp.getContent(),
        stamp.getRelatedUrl());
  }
}
