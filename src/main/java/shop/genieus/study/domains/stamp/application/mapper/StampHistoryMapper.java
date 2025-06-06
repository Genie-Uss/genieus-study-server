package shop.genieus.study.domains.stamp.application.mapper;

import org.springframework.stereotype.Component;
import shop.genieus.study.commons.provider.model.StampHistoryInfo;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;
import shop.genieus.study.domains.stamp.domain.vo.VerificationStatus;

@Component
public class StampHistoryMapper {
  public StampHistoryInfo from(StampHistory history) {
    VerificationStatus status = history.getVerificationStatus();
    return new StampHistoryInfo(
        history.getId(),
        history.getUserId(),
        history.getVerifiedAt(),
        status.getCtCount(),
        status.isCtVerified(),
        status.getTilCount(),
        status.isTilVerified(),
        status.getResumeCount(),
        status.isResumeVerified(),
        status.getTotalVerifiedCount());
  }
}
