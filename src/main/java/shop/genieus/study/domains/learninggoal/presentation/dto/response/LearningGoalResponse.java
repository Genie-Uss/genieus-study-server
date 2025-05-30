package shop.genieus.study.domains.learninggoal.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

public record LearningGoalResponse(
    Long id, String content, boolean isCompleted, LocalDateTime createdAt) {

  public static LearningGoalResponse from(LearningGoal goal) {
    return new LearningGoalResponse(
        goal.getId(), goal.getContent(), goal.isCompleted(), goal.getCreatedAt());
  }
}
