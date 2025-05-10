package shop.genieus.study.domains.stamp.presentation.dto.response;

import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;

public record ListStampResponse(List<FindStampResponse> stampResults) {
  public static ListStampResponse of(List<Stamp> stamps) {
    return new ListStampResponse(stamps.stream().map(FindStampResponse::of).toList());
  }
}
