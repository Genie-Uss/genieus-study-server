package shop.genieus.study.domains.stamp.application.dto.result;

import java.util.List;

public record GetStampSearchResult<StampView>(List<StampView> content, PageInfo page) {
  public record PageInfo(int page, int size, long totalElements, int totalPages, boolean last) {}
}
