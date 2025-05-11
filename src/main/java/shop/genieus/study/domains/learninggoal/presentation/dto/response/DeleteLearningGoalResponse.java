package shop.genieus.study.domains.learninggoal.presentation.dto.response;

public record DeleteLearningGoalResponse(boolean success) {
  public static DeleteLearningGoalResponse mock() {
    return new DeleteLearningGoalResponse(true);
  }
}
