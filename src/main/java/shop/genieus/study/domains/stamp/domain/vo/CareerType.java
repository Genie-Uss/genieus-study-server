package shop.genieus.study.domains.stamp.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareerType {
  NEWCOMER("신입"),
  EXPERIENCED("경력"),
  INTERN("인턴"),
  CONTRACT("계약직"),
  FREELANCER("프리랜서");

  private final String fieldName;
}
