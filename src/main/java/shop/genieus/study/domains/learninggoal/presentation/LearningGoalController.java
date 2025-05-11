package shop.genieus.study.domains.learninggoal.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.DeleteLearningGoalResponse;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.LearningGoalListResponse;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.LearningGoalResponse;
import shop.genieus.study.domains.learninggoal.presentation.dto.response.ToggleLearningGoalResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-goals")
public class LearningGoalController {

  // ================================== mock

  @GetMapping
  public ResponseEntity<LearningGoalListResponse> getLearningGoals( // goal list
      @RequestParam(required = false) String date) {
    return ResponseEntity.ok(LearningGoalListResponse.mock());
  }

  @PostMapping
  public ResponseEntity<LearningGoalResponse> createLearningGoal() { // goal detail
    return ResponseEntity.ok(LearningGoalResponse.mock());
  }

  @PatchMapping
  public ResponseEntity<ToggleLearningGoalResponse>
      toggleLearningGoal() { // toggle goal(목표 달성 on/off)
    return ResponseEntity.ok(ToggleLearningGoalResponse.mock());
  }

  @DeleteMapping
  public ResponseEntity<DeleteLearningGoalResponse> deleteLearningGoal() { // delete goal
    return ResponseEntity.ok(DeleteLearningGoalResponse.mock());
  }
}
