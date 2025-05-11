package shop.genieus.study.domains.attendance.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.domains.attendance.application.dto.info.CheckInInfo;
import shop.genieus.study.domains.attendance.application.dto.info.CheckOutInfo;
import shop.genieus.study.domains.attendance.application.exception.AttendanceBusinessException;
import shop.genieus.study.domains.attendance.application.exception.AttendanceNotFoundException;
import shop.genieus.study.domains.attendance.application.repository.AttendanceRepository;
import shop.genieus.study.domains.attendance.application.time.DateTimePort;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService implements AttendanceProvider {
  private final AttendanceRepository repository;
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

    Attendance attendance =
        Attendance.checkIn(
            info.userId(),
            checkInDateTime,
            userInfo.desiredCheckInTime(),
            currentDateTime.toLocalDate(),
            currentDateTime,
            userInfo.desiredCoreTime());

    return repository.save(attendance);
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

  @Override
  public boolean existsByUserIdAndDate(Long userId, LocalDate date) {
    return false;
  }
}
