package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;

public record CtStampResponse(List<Detail> details) {

  public static CtStampResponse mock() {
    return new CtStampResponse(
        List.of(
            new Detail(
                1L,
                "DYNAMIC_PROGRAMMING",
                "BAEKJOON",
                "계단 오르기 (문제번호: 2579)",
                "https://www.acmicpc.net/problem/2579")));
  }

  public static CtStampResponse of(List<CodingTestStamp> stamps){
    return new CtStampResponse(
        stamps.stream().map(Detail::of).toList()
    );
  }

  public record Detail(
      long id, String algorithmType, String platformType, String description, String problemUrl) {
    public static Detail of(CodingTestStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getAlgorithmType().getFieldName(),
          stamp.getPlatformType().getFieldName(),
          stamp.getDescription(),
          stamp.getProblemUrl()
      );
    }
  }
}
