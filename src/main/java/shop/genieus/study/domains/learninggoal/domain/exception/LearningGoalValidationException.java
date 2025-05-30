package shop.genieus.study.domains.learninggoal.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

@Getter
public class LearningGoalValidationException extends ValidationException {

  private LearningGoalValidationException(String message) {
    super(message, Domain.LEARNING_GOAL);
  }

  public static LearningGoalValidationException requiredContent() {
    return new LearningGoalValidationException("학습 목표 내용은 필수입니다.");
  }

  public static LearningGoalValidationException exceedsContentMaxLength(int maxLength) {
    return new LearningGoalValidationException("학습 목표 내용은 " + maxLength + "자를 초과할 수 없습니다.");
  }

  public static LearningGoalValidationException requiredDate() {
    return new LearningGoalValidationException("목표 날짜는 필수입니다.");
  }

  public static LearningGoalValidationException invalidDate() {
    return new LearningGoalValidationException("학습 목표는 오늘 날짜로만 설정할 수 있습니다.");
  }
}
