package shop.genieus.study.domains.attendance.application.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateTimePort {
  LocalDate getCurrentDate();

  LocalDateTime getCurrentDateTime();
}
