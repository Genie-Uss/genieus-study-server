package shop.genieus.study.domains.learninggoal.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.learninggoal.application.dto.info.CreateLearningGoalInfo;

public record CreateLearningGoalRequest(
    @NotNull(message = "날짜는 필수입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
    @NotBlank(message = "학습 목표 내용은 필수입니다.") String content) {
  public CreateLearningGoalInfo toInfo(CustomPrincipal principal) {
    return new CreateLearningGoalInfo(principal.id(), date, content);
  }
}
