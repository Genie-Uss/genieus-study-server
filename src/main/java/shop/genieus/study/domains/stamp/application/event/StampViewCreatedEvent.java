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
  public static StampViewCreatedEvent of(CodingTestStamp ct, String nickname) {
    return new StampViewCreatedEvent(
        ct.getId(),
        ct.getType(),
        null,
        ct.getUserId(),
        nickname,
        ct.getVerifiedAt(),
        ct.getAlgorithmType(),
        ct.getPlatformType(),
        null,
        null,
        null);
  }

  public static StampViewCreatedEvent of(TilStamp til, String nickname) {
    return new StampViewCreatedEvent(
        til.getId(),
        til.getType(),
        til.getTitle(),
        til.getUserId(),
        nickname,
        til.getVerifiedAt(),
        null,
        null,
        null,
        null,
        til.getCategoryType());
  }

  public static StampViewCreatedEvent of(ResumeStamp resume, String nickname) {
    return new StampViewCreatedEvent(
        resume.getId(),
        resume.getType(),
        resume.getTitle(),
        resume.getUserId(),
        nickname,
        resume.getVerifiedAt(),
        null,
        null,
        resume.getCareerType(),
        resume.getActivityType(),
        null);
  }
}
