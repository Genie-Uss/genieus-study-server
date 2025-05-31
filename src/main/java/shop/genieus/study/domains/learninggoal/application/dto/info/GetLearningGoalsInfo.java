package shop.genieus.study.domains.learninggoal.application.dto.info;

import java.time.LocalDate;

public record GetLearningGoalsInfo(Long targetUserId, Long requestUserId, LocalDate date) {}
