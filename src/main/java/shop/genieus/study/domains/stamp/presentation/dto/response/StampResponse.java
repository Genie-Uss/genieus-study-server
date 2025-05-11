package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;

public record StampResponse(LocalDate date, List<Stamp> stamps) {

  public static StampResponse mock() {
    return new StampResponse(
        LocalDate.of(2025, 5, 11),
        List.of(
            new Stamp(321L, "CODING_TEST", false),
            new Stamp(322L, "TIL", true),
            new Stamp(323L, "JOB_ACTIVITY", true)));
  }

  public record Stamp(long id, String type, boolean isVerified) {}
}
