package shop.genieus.study.domains.stamp.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {
  PORTFOLIO("포트폴리오 작성"),
  RESUME("이력서 작성"),
  INTERVIEW("면접 준비"),
  JOB_FAIR("지원서 제출"),
  NETWORKING("자기소개서 첨삭"),
  APPLICATION("이력서 첨삭"),
  COACHING("포트폴리오 첨삭"),
  OTHER("기타");

  private final String fieldName;
}
