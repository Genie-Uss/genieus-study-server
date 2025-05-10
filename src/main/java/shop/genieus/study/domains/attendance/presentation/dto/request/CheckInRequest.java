package shop.genieus.study.domains.attendance.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CheckInRequest(@NotNull(message = "출석 일시는 필수입니다.") LocalDateTime checkInDateTime) {}
