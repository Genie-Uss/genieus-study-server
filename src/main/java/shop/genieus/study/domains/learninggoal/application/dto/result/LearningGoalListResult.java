package shop.genieus.study.domains.learninggoal.application.dto.result;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

public record LearningGoalListResult(
    List<LearningGoal> goals, Long userId, LocalDate date, boolean isOwner) {}
