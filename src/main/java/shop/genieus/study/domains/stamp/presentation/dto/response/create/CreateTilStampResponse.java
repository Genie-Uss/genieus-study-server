package shop.genieus.study.domains.stamp.presentation.dto.response.create;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateTilStampResponse(
    Long id,
    StampType type,
    LocalDateTime verifiedAt,
    String title,
    CategoryType categoryType,
    String content,
    String relatedUrl) {
  public static CreateTilStampResponse of(CreateTilStampResult result) {
    return new CreateTilStampResponse(
        result.id(),
        result.type(),
        result.verifiedAt(),
        result.title(),
        result.categoryType(),
        result.content(),
        result.relatedUrl());
  }
}
