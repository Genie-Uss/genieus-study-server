package shop.genieus.study.domains.stamp.application.dto.info.create;

import shop.genieus.study.domains.stamp.application.dto.info.StampCreateInfo;
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;

public record CreateCtStampInfo(
    Long userId,
    String nickname,
    AlgorithmType algorithmType,
    PlatformType platformType,
    String description,
    String problemUrl)
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
