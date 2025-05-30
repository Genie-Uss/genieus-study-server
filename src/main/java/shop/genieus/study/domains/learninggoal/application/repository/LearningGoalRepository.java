package shop.genieus.study.domains.learninggoal.application.repository;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

public interface LearningGoalRepository {
  List<LearningGoal> findByUserIdAndDate(Long userId, LocalDate date);

  LearningGoal save(LearningGoal learningGoal);

  LearningGoal findById(Long goalId);

  void delete(LearningGoal goal);
}
