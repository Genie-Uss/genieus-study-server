package shop.genieus.study.domains.stamp.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.application.dto.info.CreateCodingTestStampInfo;
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;

public record CreateCodingTestStampRequest(
    @NotBlank(message = "알고리즘 유형을 입력해주세요.") AlgorithmType algorithmType,
    @NotBlank(message = "플랫폼 유형을 입력해주세요.") PlatformType platformType,
    @NotBlank(message = "문제 설명을 입력해주세요.") String description,
    String problemUrl) {
  public CreateCodingTestStampInfo toInfo(CustomPrincipal principal) {
    return new CreateCodingTestStampInfo(
        principal.id(), algorithmType, platformType, description, problemUrl);
  }
}
