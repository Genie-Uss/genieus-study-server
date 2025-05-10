package shop.genieus.study.domains.attendance.application.dto.info;

import java.time.LocalDateTime;

public record CheckOutInfo(Long userId, LocalDateTime checkOutDateTime) {}
