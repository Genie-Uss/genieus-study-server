package shop.genieus.study.domains.stamp.application.dto.info.create;

import shop.genieus.study.domains.stamp.application.dto.info.StampCreateInfo;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;

public record CreateTilStampInfo(
    Long userId,
    String nickname,
    String title,
    CategoryType categoryType,
    String content,
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
