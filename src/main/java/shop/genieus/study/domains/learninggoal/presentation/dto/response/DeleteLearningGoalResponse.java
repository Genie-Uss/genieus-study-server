package shop.genieus.study.domains.learninggoal.presentation.dto.response;

public record DeleteLearningGoalResponse(boolean success) {
  public static DeleteLearningGoalResponse of() {
    return new DeleteLearningGoalResponse(true);
  }
}
