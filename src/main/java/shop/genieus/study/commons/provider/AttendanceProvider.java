package shop.genieus.study.commons.provider;

import java.time.LocalDate;

public interface AttendanceProvider {
    boolean existsByUserIdAndDate(Long userId, LocalDate date);
}
