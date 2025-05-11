package shop.genieus.study.domains.attendance.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceResponse(
    Long id,
    Long userId,
    LocalDate date,
    boolean isCheckedIn,
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime,
    int studyDuration) {

  public static AttendanceResponse mock() {
    return new AttendanceResponse(
        1L,
        1L,
        LocalDate.of(2025, 5, 11),
        true,
        LocalDateTime.of(2025, 5, 11, 9, 15, 32), // 년, 월, 일, 시, 분, 초
        null,
        135);
  }
}
