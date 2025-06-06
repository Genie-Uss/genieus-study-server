package shop.genieus.study.domains.stamp.application.event.internal.model;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.vo.*;

public record StampCreatedDomainEvent(
    Long id,
    StampType type,
    String title,
    Long userId,
    String nickname,
    LocalDateTime verifiedAt,
    AlgorithmType algorithmType,
    PlatformType platformType,
    CareerType careerType,
    ActivityType activityType,
    CategoryType categoryType) {

  public static StampCreatedDomainEvent ofCodingTest(CodingTestStamp stamp, String nickname) {
    return new StampCreatedDomainEvent(
        stamp.getId(),
        stamp.getType(),
        stamp.getTitle(),
        stamp.getUserId(),
        nickname,
        stamp.getVerifiedAt(),
        stamp.getAlgorithmType(),
        stamp.getPlatformType(),
        null,
        null,
        null);
  }

  public static StampCreatedDomainEvent ofTil(TilStamp stamp, String nickname) {
    return new StampCreatedDomainEvent(
        stamp.getId(),
        stamp.getType(),
        stamp.getTitle(),
        stamp.getUserId(),
        nickname,
        stamp.getVerifiedAt(),
        null,
        null,
        null,
        null,
        stamp.getCategoryType());
  }

  public static StampCreatedDomainEvent ofResume(ResumeStamp stamp, String nickname) {
    return new StampCreatedDomainEvent(
        stamp.getId(),
        stamp.getType(),
        stamp.getTitle(),
        stamp.getUserId(),
        nickname,
        stamp.getVerifiedAt(),
        null,
        null,
        stamp.getCareerType(),
        stamp.getActivityType(),
        null);
  }
}
