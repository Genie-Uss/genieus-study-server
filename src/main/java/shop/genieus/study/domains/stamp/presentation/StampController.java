package shop.genieus.study.domains.stamp.presentation;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.application.StampCategoryService;
import shop.genieus.study.domains.stamp.application.StampHistoryService;
import shop.genieus.study.domains.stamp.application.StampService;
import shop.genieus.study.domains.stamp.application.StampViewService;
import shop.genieus.study.domains.stamp.application.dto.info.DeleteStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.*;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCtStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateResumeStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.GetStampSearchResult;
import shop.genieus.study.domains.stamp.domain.entity.*;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateCtStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateResumeStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateTilStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.StampFilterParams;
import shop.genieus.study.domains.stamp.presentation.dto.response.*;
import shop.genieus.study.domains.stamp.presentation.dto.response.create.CreateCtStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.create.CreateResumeStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.create.CreateTilStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stamps")
public class StampController {
  private final StampService stampService;
  private final StampHistoryService stampHistoryService;
  private final StampViewService stampViewService;
  private final StampCategoryService stampCategoryService;

  @PostMapping("/ct")
  public ResponseEntity<CreateCtStampResponse> createCtStamp(
      @AuthPrincipal CustomPrincipal principal, @RequestBody @Valid CreateCtStampRequest request) {
    CreateCtStampResult result = stampService.createCodingTestStamp(request.toInfo(principal));
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
    CreateResumeStampResult result = stampService.createJobActivityStamp(request.toInfo(principal));
    CreateResumeStampResponse response = CreateResumeStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<StampHistoryResponse> getStampByDate(
      @PathVariable Long userId, @RequestParam(required = false) LocalDate date) {
    StampHistory stampHistory =
        stampHistoryService.getStampHistoryByDate(new GetStampInfo(userId, date));
    return ResponseEntity.ok(StampHistoryResponse.of(stampHistory));
  }

  @GetMapping("/ct/user/{userId}")
  public ResponseEntity<CtStampResponse> getCtStampByDate(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    List<CodingTestStamp> result = stampService.getCtStampByDate(new GetCtStampInfo(userId, date));
    return ResponseEntity.ok(CtStampResponse.of(date, principal, userId, result));
  }

  @GetMapping("/til/user/{userId}")
  public ResponseEntity<TilStampResponse> getTilStampByDate(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    List<TilStamp> result = stampService.getTilStampByDate(new GetTilStampInfo(userId, date));
    return ResponseEntity.ok(TilStampResponse.of(date, principal, userId, result));
  }

  @GetMapping("/resume/user/{userId}")
  public ResponseEntity<ResumeStampResponse> getResumeStampByDate(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestParam(required = false) LocalDate date) {
    List<ResumeStamp> result =
        stampService.getResumeStampByDate(new GetResumeStampInfo(userId, date));
    return ResponseEntity.ok(ResumeStampResponse.of(date, principal, userId, result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteStampResponse> deleteStamp(
      @AuthPrincipal CustomPrincipal principal, @PathVariable(name = "id") Long stampId) {
    stampService.deleteStamp(new DeleteStampInfo(principal.id(), stampId));
    return ResponseEntity.ok(DeleteStampResponse.of());
  }

  @GetMapping("/categories")
  public ResponseEntity<StampCategoryResponse> getCategories(@RequestParam String type) {
    StampCategoryResponse categories = stampCategoryService.getCategories(type);
    return ResponseEntity.ok(categories);
  }

  @GetMapping
  public ResponseEntity<StampListResponse> getStampList(
      @ModelAttribute StampFilterParams filterParams,
      @PageableDefault(page = 0, size = 10) Pageable pageable) {
    GetStampSearchResult result =
        stampViewService.searchStampViews(GetStampSearchInfo.from(filterParams, pageable));

    return ResponseEntity.ok().body(StampListResponse.from(result));
  }

  @GetMapping("/{id}")
  public ResponseEntity<StampDetailResponse> getStampDetail(@PathVariable Long id) {
    StampDetailResponse response = StampDetailResponse.from(stampService.getStampDetail(id));
    return ResponseEntity.ok().body(response);
  }
}
