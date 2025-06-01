package shop.genieus.study.domains.stamp.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
  JAVA("Java"),
  SPRING("Spring & Spring Boot"),
  JPA("JPA & Hibernate"),
  DATABASE("Database"),
  TESTING("테스트 / TDD"),
  INFRA("인프라 & 배포"),
  CLOUD("클라우드"),
  DEVOPS("DevOps"),
  DESIGN_PATTERN("디자인 패턴"),
  ARCHITECTURE("시스템 아키텍처"),
  DATA_STRUCTURE("자료구조"),
  SECURITY("인증/인가 & 보안"),
  ALGORITHM("알고리즘"),
  CS("컴퓨터 과학 일반"),
  OTHER("기타"),
  ;

  private final String fieldName;
}
