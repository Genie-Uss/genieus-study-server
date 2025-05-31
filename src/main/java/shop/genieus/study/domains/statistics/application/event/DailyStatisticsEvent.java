package shop.genieus.study.domains.statistics.application.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.statistics.domain.vo.UserStatistics;
import shop.genieus.study.domains.statistics.domain.vo.VerificationStatusChecker;

@Getter
@RequiredArgsConstructor
public class DailyStatisticsEvent implements NotificationMessageBuilder {

  private final Long userId = -1L;
  private final LocalDate targetDate;
  private final List<UserStatistics> userStatistics;

  @Override
  public String buildTitle() {
    return "[일일 통계]";
  }

  @Override
  public String buildMessage() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n### :ledger: ")
        .append(targetDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일의 결과")))
        .append("\n\n")
        .append(createUserStatsList())
        .append("\n");

    addSummaryStatistics(sb);
    return sb.toString();
  }

  private String createUserStatsList() {
    StringBuilder sb = new StringBuilder();

    for (UserStatistics stat : userStatistics) {
      sb.append(
          String.format(
              "\n> :boy_tone3: **%s**　`[%d/4]`\n",
              stat.getNickname(), stat.getTotalVerifiedCount()));

      sb.append("> └　**`출석`**　")
          .append(VerificationStatusChecker.convertToEmoji(stat.getAttendanceStatus()))
          .append("　　**`CT`**　")
          .append(VerificationStatusChecker.convertToEmoji(stat.getCtStatus()))
          .append("　　**`TIL`**　")
          .append(VerificationStatusChecker.convertToEmoji(stat.getTilStatus()))
          .append("　　**`구직`**　")
          .append(VerificationStatusChecker.convertToEmoji(stat.getResumeStatus()))
          .append("\n");

      sb.append(String.format("> └　:money_with_wings: %,d원\n", stat.getTotalFine()));
    }

    return sb.toString();
  }

  private void addSummaryStatistics(StringBuilder sb) {
    int totalAbsent = 0;
    int totalLate = 0;
    int totalFine = 0;
    List<String> perfectUsers = new ArrayList<>();

    for (UserStatistics stat : userStatistics) {
      String attendance = stat.getAttendanceStatus();
      if (VerificationStatusChecker.isAbsent(attendance)) {
        totalAbsent++;
      } else if (VerificationStatusChecker.isLate(attendance)) {
        totalLate++;
      }

      int fine = stat.getTotalFine();
      totalFine += fine;

      if (fine == 0) {
        perfectUsers.add(stat.getNickname());
      }
    }

    int totalAttended = userStatistics.size() - totalAbsent - totalLate;

    sb.append("### :tada: 요약\n")
        .append(String.format("* **총 참여자**: %d명\n", userStatistics.size()))
        .append(
            String.format(
                "* **출석**: %d명, **지각**: %d명, **결석**: %d명\n", totalAttended, totalLate, totalAbsent))
        .append(String.format("* **총 벌금**: %,d원\n\n", totalFine));

    if (!perfectUsers.isEmpty()) {
      sb.append("### :trophy: 완벽한 하루를 보내셨군요..\n")
          .append(
              String.format(
                  "축하합니다! %s님이 완벽한 하루를 보내셨습니다! :clap:\n", String.join(", ", perfectUsers)));
    } else {
      sb.append("### :trophy: 이런..\n").append("아무도 완벽한 하루를 보내지 못했네요.. 내일은 더 화이팅! :muscle:\n");
    }
  }

  @Override
  public String getColorCode() {
    return "16761035";
  }

  @Override
  public NotificationChannelType getChannelType() {
    return NotificationChannelType.STATISTICS;
  }

  @Override
  public String getEmoji() {
    return "";
  }
}
