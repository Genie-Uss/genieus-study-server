package shop.genieus.study.domains.learninggoal.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.learninggoal.application.dto.info.GetLearningGoalsInfo;
import shop.genieus.study.domains.learninggoal.application.dto.result.LearningGoalListResult;
import shop.genieus.study.domains.learninggoal.application.repository.LearningGoalRepository;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LearningGoalQueryService {

  private final LearningGoalRepository repository;
  private final DateTimeProvider dateTimeProvider;

  public LearningGoalListResult getLearningGoals(GetLearningGoalsInfo info) {
    Long userId = info.targetUserId();
    LocalDate date = getCurrentDate(info.date());

    List<LearningGoal> goals = repository.findByUserIdAndDate(userId, date);

    log.debug("학습 목표 조회: userId={}, date={}, count={}", userId, date, goals.size());

    return new LearningGoalListResult(
        goals, userId, date, !goals.isEmpty() && goals.get(0).isOwnedBy(info.loginUserId()));
  }

  private LocalDate getCurrentDate(LocalDate date) {
    return Objects.requireNonNullElse(date, dateTimeProvider.getCurrentDate());
  }
}
