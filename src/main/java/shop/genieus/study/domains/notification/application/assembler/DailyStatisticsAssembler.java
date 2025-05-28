package shop.genieus.study.domains.notification.application.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.provider.dto.AttendanceInfo;
import shop.genieus.study.commons.provider.dto.StampHistoryInfo;
import shop.genieus.study.domains.notification.domain.vo.UserStatistics;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyStatisticsAssembler {
  public List<UserStatistics> assembleUserStatistics(
      List<Long> userIds,
      Map<Long, String> userNicknameMap,
      Map<Long, AttendanceInfo> attendanceInfos,
      Map<Long, StampHistoryInfo> stampHistoryInfos) {
    List<UserStatistics> userStatistics = new ArrayList<>();

    for (Long userId : userIds) {
      String nickname = userNicknameMap.get(userId);
      AttendanceInfo attendance = attendanceInfos.getOrDefault(userId, null);
      StampHistoryInfo stamp = stampHistoryInfos.getOrDefault(userId, createEmptyHistoryInfo());

      userStatistics.add(new UserStatistics(userId, nickname, attendance, stamp));
    }

    return userStatistics;
  }

  private StampHistoryInfo createEmptyHistoryInfo() {
    return new StampHistoryInfo(null, null, null, 0, false, 0, false, 0, false, 0);
  }
}
