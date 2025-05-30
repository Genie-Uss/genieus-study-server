package shop.genieus.study.domains.learninggoal.application;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.learninggoal.application.dto.info.CreateLearningGoalInfo;
import shop.genieus.study.domains.learninggoal.application.repository.LearningGoalRepository;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;

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
}
