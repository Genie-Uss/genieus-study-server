package shop.genieus.study.domains.attendance.infrastructure.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.attendance.application.time.DateTimePort;

@Component
@RequiredArgsConstructor
public class LocalDateTimeAdapter implements DateTimePort {
  private final DateTimeProvider dateTimeProvider;

  @Override
  public LocalDate getCurrentDate() {
    return dateTimeProvider.getCurrentDate();
  }

  @Override
  public LocalDateTime getCurrentDateTime() {
    return dateTimeProvider.getCurrentDateTime();
  }
}
