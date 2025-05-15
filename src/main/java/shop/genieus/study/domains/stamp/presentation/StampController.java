package shop.genieus.study.domains.stamp.presentation;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.application.StampCategoryService;
import shop.genieus.study.domains.stamp.application.StampHistoryService;
import shop.genieus.study.domains.stamp.application.StampService;
import shop.genieus.study.domains.stamp.application.dto.info.DeleteStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetCtStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetResumeStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetTilStampInfo;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCtStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateResumeStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateCtStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateResumeStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateTilStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.response.*;
import shop.genieus.study.domains.stamp.presentation.dto.response.create.CreateCtStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.create.CreateResumeStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.create.CreateTilStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.CtStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.ResumeStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.StampCategoryResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.StampHistoryResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.TilStampResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stamps")
public class StampController {
  private final StampService stampService;
  private final StampHistoryService stampHistoryService;
  private final StampCategoryService stampCategoryService;

  @PostMapping("/ct")
  public ResponseEntity<CreateCtStampResponse> createCtStamp(
      @AuthPrincipal CustomPrincipal principal,
      @RequestBody @Valid CreateCtStampRequest request) {
    CreateCtStampResult result =
        stampService.createCodingTestStamp(request.toInfo(principal));
    CreateCtStampResponse response = CreateCtStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/til")
  public ResponseEntity<CreateTilStampResponse> createTilStamp(
      @AuthPrincipal CustomPrincipal principal, @RequestBody @Valid CreateTilStampRequest request) {
    CreateTilStampResult result = stampService.createTilStamp(request.toInfo(principal));
    CreateTilStampResponse response = CreateTilStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/resume")
  public ResponseEntity<CreateResumeStampResponse> createResumeStamp(
      @AuthPrincipal CustomPrincipal principal,
      @RequestBody @Valid CreateResumeStampRequest request) {
    CreateResumeStampResult result =
        stampService.createJobActivityStamp(request.toInfo(principal));
    CreateResumeStampResponse response = CreateResumeStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<StampHistoryResponse> getStampByDate(
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    StampHistory stampHistory = stampHistoryService.getStampHistoryByDate(
        new GetStampInfo(userId, date));
    return ResponseEntity.ok(StampHistoryResponse.of(stampHistory));
  }

  @GetMapping("/ct/user/{userId}")
  public ResponseEntity<CtStampResponse> getCtStampByDate(
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    List<CodingTestStamp> result = stampService.getCtStampByDate(
        new GetCtStampInfo(userId, date));
    return ResponseEntity.ok(CtStampResponse.of(result));
  }

  @GetMapping("/til/user/{userId}")
  public ResponseEntity<TilStampResponse> getTilStampByDate(
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    List<TilStamp> result = stampService.getTilStampByDate(
        new GetTilStampInfo(userId, date));
    return ResponseEntity.ok(TilStampResponse.of(result));
  }

  @GetMapping("/resume/user/{userId}")
  public ResponseEntity<ResumeStampResponse> getResumeStampByDate(
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    List<ResumeStamp> result = stampService.getResumeStampByDate(
        new GetResumeStampInfo(userId, date));
    return ResponseEntity.ok(ResumeStampResponse.of(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteStampResponse> deleteStamp(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable(name = "id") Long stampId) {
    stampService.deleteStamp(new DeleteStampInfo(principal.id(),stampId));
    return ResponseEntity.ok(DeleteStampResponse.of());
  }

  @GetMapping("/categories")
  public ResponseEntity<StampCategoryResponse> getCategories(
      @RequestParam String type){
    StampCategoryResponse categories = stampCategoryService.getCategories(type);
    return ResponseEntity.ok(categories);
  }
}
