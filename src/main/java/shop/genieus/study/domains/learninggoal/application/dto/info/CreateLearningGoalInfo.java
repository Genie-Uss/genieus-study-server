package shop.genieus.study.domains.learninggoal.application.dto.info;

import java.time.LocalDate;

public record CreateLearningGoalInfo(Long userId, LocalDate date, String content) {}
