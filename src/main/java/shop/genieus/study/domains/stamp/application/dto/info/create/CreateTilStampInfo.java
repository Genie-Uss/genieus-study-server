package shop.genieus.study.domains.stamp.application.dto.info.create;

import shop.genieus.study.domains.stamp.domain.vo.CategoryType;

public record CreateTilStampInfo(
    Long userId, String title, CategoryType categoryType, String content, String relatedUrl) {}
