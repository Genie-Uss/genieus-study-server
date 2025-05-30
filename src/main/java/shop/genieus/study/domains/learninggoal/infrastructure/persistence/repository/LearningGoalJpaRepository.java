package shop.genieus.study.domains.learninggoal.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

public interface LearningGoalJpaRepository extends JpaRepository<LearningGoal, Long> {

  List<LearningGoal> findByUserIdAndDateOrderByCreatedAt(Long userId, LocalDate date);
}
