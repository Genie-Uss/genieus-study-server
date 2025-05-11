package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CtStampResponse(LocalDate date, List<Detail> details) {

  public static CtStampResponse mock() {
    return new CtStampResponse(
        LocalDate.of(2025, 5, 11),
        List.of(
            new Detail(
                1L,
                "DYNAMIC_PROGRAMMING",
                "BAEKJOON",
                "계단 오르기 (문제번호: 2579)",
                "https://www.acmicpc.net/problem/2579")));
  }

  public record Detail(
      long id, String algorithmType, String platformType, String description, String problemUrl) {}
}
