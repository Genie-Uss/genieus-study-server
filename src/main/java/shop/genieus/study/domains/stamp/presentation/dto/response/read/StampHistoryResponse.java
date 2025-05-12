package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.time.LocalDate;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;
import shop.genieus.study.domains.stamp.domain.vo.VerificationStatus;

public record StampHistoryResponse(
    Long userId,
    LocalDate verifiedAt,
    int ctCount,
    boolean ctVerified,
    int tilCount,
    boolean tilVerified,
    int resumeCount,
    boolean resumeVerified,
    int totalVerifiedCount) {
  public static StampHistoryResponse of(StampHistory entity) {
    VerificationStatus status = entity.getVerificationStatus();
    return new StampHistoryResponse(
        entity.getUserId(),
        entity.getVerifiedAt(),
        status.getCtCount(),
        status.isCtVerified(),
        status.getTilCount(),
        status.isTilVerified(),
        status.getResumeCount(),
        status.isResumeVerified(),
        status.getTotalVerifiedCount());
  }
}
