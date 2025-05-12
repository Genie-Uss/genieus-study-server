package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDateTime;
import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;

public record CtStampResponse(List<Detail> details) {

  public static CtStampResponse of(List<CodingTestStamp> stamps) {
    return new CtStampResponse(stamps.stream().map(Detail::of).toList());
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
