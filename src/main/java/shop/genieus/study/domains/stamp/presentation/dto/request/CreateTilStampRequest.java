package shop.genieus.study.domains.stamp.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateTilStampInfo;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;

public record CreateTilStampRequest(
    @NotBlank(message = "제목을 입력해주세요.") String title,
    @NotBlank(message = "카테고리 유형을 입력해주세요.") CategoryType categoryType,
    @NotBlank(message = "내용을 입력해주세요.") String content,
    String relatedUrl) {
  public CreateTilStampInfo toInfo(CustomPrincipal principal) {
    return new CreateTilStampInfo(principal.id(), title, categoryType, content, relatedUrl);
  }
}
