package shop.genieus.study.domains.user.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParticipationStatus {
  ACTIVE("활성 참여"),
  INACTIVE("비활성 참여"),
  TEMPORARY_LEAVE("일시 휴가"),
  ;

  private final String description;
}
