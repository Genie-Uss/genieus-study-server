package shop.genieus.study.domains.attendance.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CheckOutRequest(@NotNull(message = "퇴실 일시는 필수입니다.") LocalDateTime checkOutDateTime) {}
