package shop.genieus.study.domains.learninggoal.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;

public record LearningGoalListResponse(List<LearningGoalResponse> goals, boolean isOwner) {
  public static LearningGoalListResponse mock() {
    LocalDate today = LocalDate.now();
    List<LearningGoalResponse> goals =
        List.of(
            new LearningGoalResponse(789L, today, "React 컴포넌트 생명주기와 Hooks 이해하기", true),
            new LearningGoalResponse(790L, today, "MUI 라이브러리를 활용한 컴포넌트 스타일링 구현하기", false));

    return new LearningGoalListResponse(goals, true);
  }
}
