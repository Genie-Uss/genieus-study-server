package shop.genieus.study.domains.stamp.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlgorithmType {
  DATA_STRUCTURE("자료구조"),
  GREEDY("그리디"),
  DYNAMIC_PROGRAMMING("다이나믹 프로그래밍"),
  GRAPH("그래프"),
  TREE("트리"),
  SORTING("정렬"),
  SEARCHING("검색"),
  HASH("해시"),
  MATH("수학"),
  IMPLEMENTATION("구현"),
  BFS_DFS("BFS/DFS"),
  OTHER("기타");

  private final String fieldName;

}
