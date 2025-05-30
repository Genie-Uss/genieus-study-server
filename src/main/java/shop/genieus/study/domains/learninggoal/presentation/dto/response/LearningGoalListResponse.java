package shop.genieus.study.domains.learninggoal.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.domains.learninggoal.application.dto.result.LearningGoalListResult;

public record LearningGoalListResponse(
    Long userId, LocalDate date, boolean isOwner, List<LearningGoalResponse> data) {

  public static LearningGoalListResponse from(LearningGoalListResult result) {
    return new LearningGoalListResponse(
        result.userId(),
        result.date(),
        result.isOwner(),
        result.goals().stream().map(LearningGoalResponse::from).toList());
  }
}
