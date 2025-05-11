package shop.genieus.study.domains.learninggoal.presentation.dto.response;

import java.time.LocalDate;

public record LearningGoalResponse(long id, LocalDate date, String content, boolean isCompleted) {
  public static LearningGoalResponse mock() {
    LocalDate today = LocalDate.now();
    return new LearningGoalResponse(789L, today, "React 컴포넌트 생명주기와 Hooks 이해하기", true);
  }
}
