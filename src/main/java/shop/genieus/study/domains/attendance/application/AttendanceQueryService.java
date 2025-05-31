package shop.genieus.study.domains.attendance.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.AttendanceInfo;
import shop.genieus.study.commons.provider.dto.UserSettingHistoryInfo;
import shop.genieus.study.domains.attendance.application.dto.info.GetAttendanceInfo;
import shop.genieus.study.domains.attendance.application.dto.result.AttendanceResult;
import shop.genieus.study.domains.attendance.application.exception.AttendanceNotFoundException;
import shop.genieus.study.domains.attendance.application.mapper.AttendanceMapper;
import shop.genieus.study.domains.attendance.application.repository.AttendanceRepository;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceQueryService implements AttendanceProvider {
  private final AttendanceRepository repository;
  private final AttendanceMapper mapper;
  private final UserProvider userProvider;
  private final DateTimeProvider dateTimeProvider;

  @Transactional(readOnly = true)
  public AttendanceResult getAttendance(GetAttendanceInfo info) {
    Long targetUserId = info.targetUserId();
    Long requestUserId = info.requestUserId();
    LocalDate targetDate = getCurrentDate(info.targetDate());

    try {
      Attendance attendance =
          repository.findByUserIdAndAttendanceTimeDate(targetUserId, targetDate);

      return AttendanceResult.from(attendance, requestUserId);
    } catch (AttendanceNotFoundException e) {
      log.info("{}의 출석 정보를 찾을 수 없음", info.targetDate());

      UserSettingHistoryInfo settingInfo =
          userProvider.getEffectiveSettingsByDate(targetUserId, targetDate);

      return AttendanceResult.notFound(
          targetUserId,
          requestUserId,
          settingInfo.desiredCoreTime(),
          targetDate,
          settingInfo.desiredCheckInTime());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Map<Long, AttendanceInfo> getAttendances(List<Long> userIds, LocalDate date) {
    List<Attendance> existingAttendances =
        repository.findByUserIdsAndAttendanceTimeDate(userIds, date);

    return existingAttendances.stream()
        .collect(Collectors.toMap(att -> att.getUserId(), att -> mapper.from(att)));
  }

  @Override
  public boolean existsByUserIdAndDate(Long userId, LocalDate date) {
    return repository.existsByUserIdAndAttendanceTimeDate(userId, date);
  }

  private LocalDate getCurrentDate(LocalDate date) {
    return Objects.requireNonNullElse(date, dateTimeProvider.getCurrentDate());
  }
}
