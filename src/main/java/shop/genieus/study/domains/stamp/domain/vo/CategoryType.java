package shop.genieus.study.domains.stamp.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
  DEVELOPMENT("개발"),
  ALGORITHM("알고리즘"),
  FRONTEND("프론트엔드"),
  BACKEND("백엔드"),
  DATABASE("데이터베이스"),
  CLOUD("클라우드"),
  INFRASTRUCTURE("인프라"),
  DEVOPS("DevOps"),
  DATA_SCIENCE("데이터 사이언스"),
  DESIGN("디자인"),
  OTHER("기타");

  private final String fieldName;
}
