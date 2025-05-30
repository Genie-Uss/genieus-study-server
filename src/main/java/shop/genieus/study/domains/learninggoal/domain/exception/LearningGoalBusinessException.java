package shop.genieus.study.domains.learninggoal.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.BusinessException;
import shop.genieus.study.commons.exception.Domain;

@Getter
public class LearningGoalBusinessException extends BusinessException {

  private LearningGoalBusinessException(String message) {
    super(message, Domain.LEARNING_GOAL);
  }

  public static LearningGoalBusinessException noPermissionForGoal() {
    return new LearningGoalBusinessException("해당 학습 목표에 권한이 없습니다.");
  }
}
