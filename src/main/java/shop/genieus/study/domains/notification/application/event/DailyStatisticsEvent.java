package shop.genieus.study.domains.notification.application.event;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.notification.domain.vo.UserStatistics;

@Getter
@RequiredArgsConstructor
public class DailyStatisticsEvent implements NotificationMessageBuilder {
  private final Long userId = -1L;
  private final LocalDate targetDate;
  private final List<UserStatistics> userStatistics;

  @Override
  public String buildTitle() {
    return String.format(
        "**[일일 통계] %s 활동 결과**",
        targetDate.format(java.time.format.DateTimeFormatter.ofPattern("MM월 dd일")));
  }

  @Override
  public String buildMessage() {
    StringBuilder sb = new StringBuilder();

    sb.append(createMarkdownTable());
    sb.append("\n");
    addSummaryStatistics(sb);

    return sb.toString();
  }

  private String createMarkdownTable() {
    StringBuilder sb = new StringBuilder();

    // 마크다운 표 헤더
    sb.append("| 이름 | 출석 | CT | TIL | 구직 | 통계 | 벌금 |\n");
    sb.append("|------|------|----|----|-----|------|------|\n");

    // 표 내용
    for (UserStatistics stat : userStatistics) {
      sb.append(
          String.format(
              "| %s | %s | %s | %s | %s | %d/4 | %,d원 |\n",
              truncateName(stat.getNickname()),
              cleanStatus(stat.getAttendanceStatus()),
              cleanStatus(stat.getCtStatus()),
              cleanStatus(stat.getTilStatus()),
              cleanStatus(stat.getResumeStatus()),
              stat.getTotalVerifiedCount(),
              stat.getTotalFine()));
    }

    return sb.toString();
  }

  private void addSummaryStatistics(StringBuilder sb) {
    int totalAbsent =
        (int) userStatistics.stream().filter(s -> s.getAttendanceStatus().contains("결석")).count();
    int totalLate =
        (int) userStatistics.stream().filter(s -> s.getAttendanceStatus().contains("지각")).count();
    int totalAttended = userStatistics.size() - totalAbsent - totalLate;
    int totalFine = userStatistics.stream().mapToInt(UserStatistics::getTotalFine).sum();

    sb.append("**📈 요약**\n");
    sb.append(String.format("• 총 참여자: %d명\n", userStatistics.size()));
    sb.append(
        String.format("• 출석: %d명, 지각: %d명, 결석: %d명\n", totalAttended, totalLate, totalAbsent));
    sb.append(String.format("• 총 벌금: %,d원\n", totalFine));

    List<String> perfectUsers =
        userStatistics.stream()
            .filter(s -> s.getTotalFine() == 0)
            .map(UserStatistics::getNickname)
            .collect(Collectors.toList());

    if (!perfectUsers.isEmpty()) {
      sb.append(String.format("• 🏆 완벽한 하루: %s\n", String.join(", ", perfectUsers)));
    }
  }

  private String truncateName(String name) {
    return name.length() > 8 ? name.substring(0, 6) + ".." : name;
  }

  private String cleanStatus(String status) {
    return status
        .replace("`", "")
        .replace("인증 없음", "❌")
        .replace("출석", "✅")
        .replace("지각", "⏰")
        .replace("결석", "❌")
        .replace("O", "✅")
        .replace("X", "❌");
  }

  @Override
  public String getColorCode() {
    return "3066993";
  }

  @Override
  public NotificationChannelType getChannelType() {
    return NotificationChannelType.STATISTICS;
  }

  @Override
  public String getEmoji() {
    return "📊 ";
  }
}
