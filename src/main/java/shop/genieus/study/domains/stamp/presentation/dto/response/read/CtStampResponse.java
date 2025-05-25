package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;

public record CtStampResponse(LocalDate date, boolean isOwner, List<Detail> details) {

  public static CtStampResponse of(LocalDate date, CustomPrincipal principal, Long targetUserId, List<CodingTestStamp> stamps) {
    return new CtStampResponse(date, isOwner(principal.id(), targetUserId), stamps.stream().map(Detail::of).toList());
  }

  private static boolean isOwner(Long requestUserId, Long targetUserId) {
    return Objects.equals(requestUserId, targetUserId);
  }

  public record Detail(
      Long id,
      Long userId,
      LocalDateTime verifiedAt,
      String algorithmType,
      String platformType,
      String description,
      String problemUrl) {
    public static Detail of(CodingTestStamp stamp) {
      return new Detail(
          stamp.getId(),
          stamp.getUserId(),
          stamp.getVerifiedAt(),
          stamp.getAlgorithmType().getFieldName(),
          stamp.getPlatformType().getFieldName(),
          stamp.getDescription(),
          stamp.getProblemUrl());
    }
  }
}
