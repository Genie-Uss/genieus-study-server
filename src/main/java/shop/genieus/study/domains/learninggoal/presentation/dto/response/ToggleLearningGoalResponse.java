package shop.genieus.study.domains.learninggoal.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

public record ToggleLearningGoalResponse(Long id, boolean isCompleted, LocalDateTime updatedAt) {

  public static ToggleLearningGoalResponse from(LearningGoal goal) {
    return new ToggleLearningGoalResponse(goal.getId(), goal.isCompleted(), goal.getUpdatedAt());
  }
}
