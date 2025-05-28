package shop.genieus.study.commons.provider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import shop.genieus.study.commons.provider.dto.StampHistoryInfo;

public interface StampHistoryProvider {
  Map<Long, StampHistoryInfo> getStampHistories(List<Long> userIds, LocalDate date);
}
