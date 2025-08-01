package shop.genieus.study.domains.statistics.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FinePolicy {
  NO_ATTENDANCE(500, "미출석"),
  LATE_ATTENDANCE(500, "지각"),
  MISSING_CT(500, "코딩테스트 미인증"),
  MISSING_TIL(500, "TIL 미인증"),
  MISSING_RESUME(500, "구직활동 미인증"),
  
  STAMP_GROUP_0_SUCCESS(1000, "스탬프 0개 성공"),
  STAMP_GROUP_1_SUCCESS(500, "스탬프 1개 성공");

  private final int amount;
  private final String description;
}
