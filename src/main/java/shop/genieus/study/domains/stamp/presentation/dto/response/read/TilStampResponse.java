package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;

public record TilStampResponse(List<Detail> details) {

  public static TilStampResponse mock() {
    return new TilStampResponse(
        List.of(
            new Detail(
                122L,
                "React Hooks의 종류와 활용법",
                "FRONTEND",
                "오늘은 React의 다양한 Hooks에 대해 학습했다...",
                "https://github.com/myusername/til/react-hooks.md")));
  }

  public static TilStampResponse of(List<TilStamp> stamps) {
    return new TilStampResponse(stamps.stream().map(Detail::of).toList());
  }

  public record Detail(
      long id, String title, String categoryType, String content, String relatedUrl) {
    public static Detail of(TilStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getTitle(),
          stamp.getCategoryType().getFieldName(),
          stamp.getContent(),
          stamp.getRelatedUrl());
    }
  }
}
