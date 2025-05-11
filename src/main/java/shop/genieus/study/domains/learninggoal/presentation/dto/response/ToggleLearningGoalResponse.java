package shop.genieus.study.domains.learninggoal.presentation.dto.response;

public record ToggleLearningGoalResponse(Long id, boolean isCompleted) {
  public static ToggleLearningGoalResponse mock() {
    return new ToggleLearningGoalResponse(123L, false);
  }
}
