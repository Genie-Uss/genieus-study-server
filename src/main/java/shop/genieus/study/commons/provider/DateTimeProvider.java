package shop.genieus.study.commons.provider;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DateTimeProvider {
  private  final Clock clock;

  public LocalDate getCurrentDate() {
    return LocalDate.now(clock);
  }

  public LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now(clock);
  }
}
