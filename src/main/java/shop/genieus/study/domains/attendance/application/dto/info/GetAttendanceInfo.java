package shop.genieus.study.domains.attendance.application.dto.info;

import java.time.LocalDate;

public record GetAttendanceInfo(Long targetUserId, LocalDate targetDate) {}
