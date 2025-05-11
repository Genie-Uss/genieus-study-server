package shop.genieus.study.domains.stamp.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlatformType {
  BAEKJOON("백준"),
  PROGRAMMERS("프로그래머스"),
  LEETCODE("릿코드"),
  CODEFORCES("코드포스"),
  HACKERRANK("HackerRank"),
  ALGOSPOT("알고스팟"),
  SWEA("SWEA"),
  INFORMATICS("정보올림피아드"),
  CODING_TEST("코딩테스트"),
  OTHER("기타");

  private final String fieldName;
}
