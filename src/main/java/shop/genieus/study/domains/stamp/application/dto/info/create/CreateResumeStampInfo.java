package shop.genieus.study.domains.stamp.application.dto.info.create;

import shop.genieus.study.domains.stamp.application.dto.info.StampCreateInfo;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;

public record CreateResumeStampInfo(
    Long userId,
    String nickname,
    String title,
    CareerType careerType,
    ActivityType activityType,
    String description,
    String relatedUrl)
    implements StampCreateInfo {

  @Override
  public Long getUserId() {
    return userId;
  }

  @Override
  public String getNickname() {
    return nickname;
  }
}
