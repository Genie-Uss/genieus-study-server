package shop.genieus.study.commons.provider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import shop.genieus.study.commons.provider.dto.AttendanceInfo;

public interface AttendanceProvider {
  boolean existsByUserIdAndDate(Long userId, LocalDate date);

  Map<Long, AttendanceInfo> getAttendances(List<Long> userIds, LocalDate date);
}
