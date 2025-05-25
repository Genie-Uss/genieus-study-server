package shop.genieus.study.commons.provider.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserSettingHistoryInfo(
    Long id,
    Long userId,
    LocalDate effectiveFromDate,
    LocalDate effectiveToDate,
    LocalTime desiredCheckInTime,
    Integer desiredCoreTime,
    Boolean isActive) {}
