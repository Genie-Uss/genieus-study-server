package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record StampDetailResponse(
    Long id,
    Long userId,
    StampType type,
    LocalDateTime verifiedAt,
    String[] categories,
    String title,
    String content,
    String url) {
  public static StampDetailResponse from(Stamp stamp) {
    return new StampDetailResponse(
        stamp.getId(),
        stamp.getUserId(),
        stamp.getType(),
        stamp.getVerifiedAt(),
        stamp.getCategories(),
        stamp.getTitle(),
        stamp.getContent(),
        stamp.getUrl());
  }
}
