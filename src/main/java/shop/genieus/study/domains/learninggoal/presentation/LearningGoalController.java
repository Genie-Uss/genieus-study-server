package shop.genieus.study.domains.learninggoal.presentation;

import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.learninggoal.application.LearningGoalCommandService;
import shop.genieus.study.domains.learninggoal.application.LearningGoalQueryService;
import shop.genieus.study.domains.learninggoal.application.dto.info.DeleteLearningGoalInfo;
import shop.genieus.study.domains.learninggoal.application.dto.info.GetLearningGoalsInfo;
import shop.genieus.study.domains.learninggoal.application.dto.info.ToggleLearningGoalInfo;
import shop.genieus.study.domains.learninggoal.application.dto.result.LearningGoalListResult;
import shop.genieus.study.domains.learninggoal.domain.entity.LearningGoal;
import shop.genieus.study.domains.learninggoal.presentation.dto.request.CreateLearningGoalRequest;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.DeleteLearningGoalResponse;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.LearningGoalListResponse;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.LearningGoalResponse;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.ToggleLearningGoalResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-goals")
public class LearningGoalController {

  private final LearningGoalQueryService queryService;
  private final LearningGoalCommandService commandService;

  @GetMapping
  public ResponseEntity<LearningGoalListResponse> getLearningGoals(
      @AuthPrincipal CustomPrincipal principal,
      @RequestParam(required = false) LocalDate date,
      @RequestParam Long userId) {
    LearningGoalListResult result =
        queryService.getLearningGoals(new GetLearningGoalsInfo(userId, principal.id(), date));

    return ResponseEntity.ok().body(LearningGoalListResponse.from(result));
  }

  @PostMapping
  public ResponseEntity<LearningGoalResponse> createLearningGoal(
      @AuthPrincipal CustomPrincipal principal,
      @RequestBody @Valid CreateLearningGoalRequest request) {
    LearningGoal goal = commandService.createLearningGoal(request.toInfo(principal));

    return ResponseEntity.ok().body(LearningGoalResponse.from(goal));
  }

  @PatchMapping("/{goalId}")
  public ResponseEntity<ToggleLearningGoalResponse> toggleLearningGoal(
      @AuthPrincipal CustomPrincipal principal, @PathVariable Long goalId) {
    LearningGoal goal =
        commandService.toggleLearningGoal(new ToggleLearningGoalInfo(principal.id(), goalId));

    return ResponseEntity.ok().body(ToggleLearningGoalResponse.from(goal));
  }

  @DeleteMapping("/{goalId}")
  public ResponseEntity<DeleteLearningGoalResponse> deleteLearningGoal(
      @AuthPrincipal CustomPrincipal principal, @PathVariable Long goalId) {
    commandService.deleteLearningGoal(new DeleteLearningGoalInfo(principal.id(), goalId));

    return ResponseEntity.ok().body(DeleteLearningGoalResponse.of());
  }
}
