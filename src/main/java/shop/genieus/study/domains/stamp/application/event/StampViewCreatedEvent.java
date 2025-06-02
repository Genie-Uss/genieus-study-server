package shop.genieus.study.domains.stamp.application.event;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.vo.*;

public record StampViewCreatedEvent(
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

  public static StampViewCreatedEvent ofCodingTest(CodingTestStamp stamp, String nickname) {
    return new StampViewCreatedEvent(
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

  public static StampViewCreatedEvent ofTil(TilStamp stamp, String nickname) {
    return new StampViewCreatedEvent(
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

  public static StampViewCreatedEvent ofResume(ResumeStamp stamp, String nickname) {
    return new StampViewCreatedEvent(
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
