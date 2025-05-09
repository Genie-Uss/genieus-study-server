package shop.genieus.study.domains.stamp.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.genieus.study.domains.auth.presentation.annotation.CustomPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.AuthPrincipal;
import shop.genieus.study.domains.stamp.application.StampService;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCodingTestStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateJobActivityStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateCodingTestStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateJobActivityStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.request.CreateTilStampRequest;
import shop.genieus.study.domains.stamp.presentation.dto.response.CreateCodingTestStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.CreateJobActivityStampResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.CreateTilStampResponse;

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
}
