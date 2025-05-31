package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.util.List;
import shop.genieus.study.domains.stamp.application.dto.result.GetStampSearchResult;
import shop.genieus.study.domains.stamp.domain.entity.StampView;

public record StampListResponse(List<StampView> content, GetStampSearchResult.PageInfo page) {
  public static StampListResponse from(GetStampSearchResult result) {
    return new StampListResponse(result.content(), result.page());
  }
}
