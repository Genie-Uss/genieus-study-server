package shop.genieus.study.domains.learninggoal.application;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.learninggoal.application.dto.info.CreateLearningGoalInfo;
import shop.genieus.study.domains.learninggoal.application.dto.info.DeleteLearningGoalInfo;
import shop.genieus.study.domains.learninggoal.application.dto.info.ToggleLearningGoalInfo;
import shop.genieus.study.domains.learninggoal.application.repository.LearningGoalRepository;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;
import shop.genieus.study.domains.learninggoal.domain.exception.LearningGoalBusinessException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LearningGoalCommandService {
  private final LearningGoalRepository repository;
  private final DateTimeProvider dateTimeProvider;

  public LearningGoal createLearningGoal(CreateLearningGoalInfo info) {
    LocalDate today = dateTimeProvider.getCurrentDate();
    LearningGoal learningGoal =
        LearningGoal.create(info.userId(), info.date(), info.content(), today);
    LearningGoal saved = repository.save(learningGoal);

    log.info(
        "학습 목표 생성: userId={}, date={}, goalId={}",
        saved.getUserId(),
        saved.getDate(),
        saved.getId());

    return saved;
  }

  public LearningGoal toggleLearningGoal(ToggleLearningGoalInfo info) {
    LearningGoal learningGoal = repository.findById(info.goalId());
    validateOwnership(learningGoal, info.userId());

    learningGoal.toggleCompletion();
    LearningGoal updated = repository.save(learningGoal);

    log.info(
        "학습 목표 완료 상태 변경: userId={}, goalId={}, completed={}",
        updated.getUserId(),
        updated.getId(),
        updated.getIsCompleted());

    return updated;
  }

  public void deleteLearningGoal(DeleteLearningGoalInfo info) {
    Long userId = info.userId();
    Long goalId = info.goalId();

    LearningGoal goal = repository.findById(goalId);
    validateOwnership(goal, userId);
    repository.delete(goal);

    log.info("학습 목표 삭제: userId={}, goalId={}", userId, goalId);
  }

  private void validateOwnership(LearningGoal learningGoal, Long userId) {
    if (!learningGoal.isOwnedBy(userId)) {
      throw LearningGoalBusinessException.noPermissionForGoal();
    }
  }
}
