package shop.genieus.study.domains.stamp.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateResumeStampInfo;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;

public record CreateResumeStampRequest(
    @NotBlank(message = "회사명을 입력해주세요.") String companyName,
    @NotNull(message = "경력 유형을 입력해주세요.") CareerType careerType,
    @NotNull(message = "활동 유형을 입력해주세요.") ActivityType activityType,
    @NotBlank(message = "상세 내용을 입력해주세요.") String description,
    String relatedUrl) {
  public CreateResumeStampInfo toInfo(CustomPrincipal principal) {
    return new CreateResumeStampInfo(
        principal.id(), companyName, careerType, activityType, description, relatedUrl);
  }
}
