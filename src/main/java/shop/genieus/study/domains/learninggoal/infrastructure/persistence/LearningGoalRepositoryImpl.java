package shop.genieus.study.domains.learninggoal.infrastructure.persistence;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.learninggoal.application.repository.LearningGoalRepository;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;
import shop.genieus.study.domains.learninggoal.infrastructure.persistence.repository.LearningGoalJpaRepository;

@Repository
@RequiredArgsConstructor
public class LearningGoalRepositoryImpl implements LearningGoalRepository {

  private final LearningGoalJpaRepository jpaRepository;

  @Override
  public List<LearningGoal> findByUserIdAndDate(Long userId, LocalDate date) {
    return jpaRepository.findByUserIdAndDate(userId, date);
  }
}
