package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.util.List;
import shop.genieus.study.domains.stamp.application.dto.result.VerifiedStampResult;

public record VerifiedStampResponse(List<VerifiedStampResult> stamps) {
  public static VerifiedStampResponse of(List<VerifiedStampResult> results) {
    return new VerifiedStampResponse(results);
  }
}
