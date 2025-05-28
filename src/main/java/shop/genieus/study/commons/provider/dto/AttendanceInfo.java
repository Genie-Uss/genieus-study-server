package shop.genieus.study.commons.provider.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AttendanceInfo(
    Long id,
    Long userId,
    LocalDate date,
    int desiredCoreTime,
    boolean hasAttendance,
    LocalDateTime checkInTime,
    boolean isCheckedIn,
    LocalDateTime checkOutTime,
    boolean isCheckedOut,
    boolean isLate,
    LocalTime desiredCheckInTime,
    int studyMinutes,
    double achievementRate) {}
