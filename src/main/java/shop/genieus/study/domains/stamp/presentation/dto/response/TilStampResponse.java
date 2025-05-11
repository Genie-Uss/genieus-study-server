package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;

public record TilStampResponse(LocalDate date, List<Detail> details) {

  public static TilStampResponse mock() {
    return new TilStampResponse(
        LocalDate.of(2025, 5, 11),
        List.of(
            new Detail(
                122L,
                "React Hooks의 종류와 활용법",
                "FRONTEND",
                "오늘은 React의 다양한 Hooks에 대해 학습했다...",
                "https://github.com/myusername/til/react-hooks.md")));
  }

  public record Detail(
      long id, String title, String categoryType, String content, String relatedUrl) {}
}
