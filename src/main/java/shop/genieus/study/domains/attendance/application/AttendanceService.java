package shop.genieus.study.domains.attendance.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.AttendanceInfo;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.commons.provider.dto.UserSettingHistoryInfo;
import shop.genieus.study.domains.attendance.application.dto.info.CheckInInfo;
import shop.genieus.study.domains.attendance.application.dto.info.CheckOutInfo;
import shop.genieus.study.domains.attendance.application.dto.info.GetAttendanceInfo;
import shop.genieus.study.domains.attendance.application.dto.result.AttendanceResult;
import shop.genieus.study.domains.attendance.application.exception.AttendanceBusinessException;
import shop.genieus.study.domains.attendance.application.exception.AttendanceNotFoundException;
import shop.genieus.study.domains.attendance.application.mapper.AttendanceMapper;
import shop.genieus.study.domains.attendance.application.repository.AttendanceRepository;
import shop.genieus.study.domains.attendance.application.time.DateTimePort;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService implements AttendanceProvider {
  private final AttendanceRepository repository;
  private final AttendanceMapper mapper;
  private final UserProvider userProvider;
  private final DateTimePort dateTimePort;

  public Attendance checkIn(CheckInInfo info) {
    LocalDateTime currentDateTime = dateTimePort.getCurrentDateTime();

    LocalDateTime checkInDateTime = info.checkInDateTime();
    LocalDate checkInDate = checkInDateTime.toLocalDate();

    if (repository.existsByUserIdAndAttendanceTimeDate(info.userId(), checkInDate)) {
      throw AttendanceBusinessException.alreadyCheckedIn();
    }

    UserInfo userInfo = userProvider.findByUserId(info.userId());

    try {
      Attendance attendance =
          Attendance.checkIn(
              info.userId(),
              checkInDateTime,
              userInfo.desiredCheckInTime(),
              currentDateTime.toLocalDate(),
              currentDateTime,
              userInfo.desiredCoreTime());
      return repository.save(attendance);
    } catch (Exception e) {
      log.warn("출석 처리 중 오류- check in info: {}, 현재 시각: {}", info, currentDateTime);
      throw e;
    }
  }

  public Attendance checkOut(CheckOutInfo info) {
    LocalDateTime currentDateTime = dateTimePort.getCurrentDateTime();
    LocalDate currentDate = currentDateTime.toLocalDate();

    Attendance attendance;
    try {
      attendance = repository.findByUserIdAndAttendanceTimeDate(info.userId(), currentDate);
    } catch (AttendanceNotFoundException e) {
      log.info("퇴실 실패: {}", e.getMessage());
      throw AttendanceBusinessException.notExistAttendance();
    }

    if (attendance.isCheckedOut()) {
      log.info("이미 퇴실 처리되었습니다.");
      throw AttendanceBusinessException.alreadyCheckedOut();
    }

    attendance.checkOut(info.checkOutDateTime(), currentDate, currentDateTime);

    return repository.save(attendance);
  }

  @Transactional(readOnly = true)
  public AttendanceResult getAttendance(GetAttendanceInfo info) {
    Long targetUserId = info.targetUserId();
    LocalDate targetDate = info.targetDate();

    try {
      Attendance attendance =
          repository.findByUserIdAndAttendanceTimeDate(targetUserId, targetDate);

      return AttendanceResult.from(attendance);
    } catch (AttendanceNotFoundException e) {
      log.info("{}의 출석 정보를 찾을 수 없음", info.targetDate());

      UserSettingHistoryInfo settingInfo =
          userProvider.getEffectiveSettingsByDate(targetUserId, targetDate);

      return AttendanceResult.notFound(
          targetUserId,
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
}
