package shop.genieus.study.commons.provider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DateTimeProvider {
  public LocalDate getCurrentDate() {
    return LocalDate.now();
  }

  public LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now();
  }
}
