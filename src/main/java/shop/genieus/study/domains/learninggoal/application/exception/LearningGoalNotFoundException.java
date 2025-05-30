package shop.genieus.study.domains.learninggoal.application.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.NotFoundException;

@Getter
public class LearningGoalNotFoundException extends NotFoundException {

  private LearningGoalNotFoundException(String message) {
    super(message, Domain.LEARNING_GOAL);
  }

  public static LearningGoalNotFoundException create(Long id) {
    return new LearningGoalNotFoundException("해당 학습 목표를 찾을 수 없습니다. ID: " + id);
  }
}
