package shop.genieus.study.domains.stamp.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.application.StampService;
import shop.genieus.study.domains.stamp.application.dto.info.FindTodayStampInfo;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCodingTestStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateJobActivityStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateCodingTestStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateJobActivityStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateTilStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.response.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stamps")
public class StampController {
  private final StampService stampService;

  @PostMapping("/coding-test")
  public ResponseEntity<CreateCodingTestStampResponse> createCodingTestStamp(
      @AuthPrincipal CustomPrincipal principal,
      @RequestBody @Valid CreateCodingTestStampRequest request) {
    CreateCodingTestStampResult result =
        stampService.createCodingTestStamp(request.toInfo(principal));
    CreateCodingTestStampResponse response = CreateCodingTestStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/til")
  public ResponseEntity<CreateTilStampResponse> createTilStamp(
      @AuthPrincipal CustomPrincipal principal, @RequestBody @Valid CreateTilStampRequest request) {
    CreateTilStampResult result = stampService.createTilStamp(request.toInfo(principal));
    CreateTilStampResponse response = CreateTilStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/job-activity")
  public ResponseEntity<CreateJobActivityStampResponse> createJobActivityStamp(
      @AuthPrincipal CustomPrincipal principal,
      @RequestBody @Valid CreateJobActivityStampRequest request) {
    CreateJobActivityStampResult result =
        stampService.createJobActivityStamp(request.toInfo(principal));
    CreateJobActivityStampResponse response = CreateJobActivityStampResponse.of(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/today")
  public ResponseEntity<ListStampResponse> findTodayStamps(
      @AuthPrincipal CustomPrincipal principal) {
    ListStampResponse response =
        stampService.findTodayStamps(new FindTodayStampInfo(principal.id()));
    return ResponseEntity.ok(response);
  }

  // ================================== mock

  @GetMapping
  public ResponseEntity<StampResponse> getStampByDate(@RequestParam(required = false) String date) {
    return ResponseEntity.ok().body(StampResponse.mock());
  }

  @GetMapping("/ct")
  public ResponseEntity<CtStampResponse> getCtStampByDate(
      @RequestParam(required = false) String date) {
    return ResponseEntity.ok().body(CtStampResponse.mock());
  }

  @GetMapping("/til")
  public ResponseEntity<TilStampResponse> getTilStampByDate(
      @RequestParam(required = false) String date) {
    return ResponseEntity.ok().body(TilStampResponse.mock());
  }

  @GetMapping("/resume")
  public ResponseEntity<ResumeStampResponse> getResumeStampByDate(
      @RequestParam(required = false) String date) {
    return ResponseEntity.ok().body(ResumeStampResponse.mock());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteStampResponse> deleteStamp() {
    return ResponseEntity.ok().body(DeleteStampResponse.mock());
  }
}
