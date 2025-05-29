package shop.genieus.study.domains.notification.domain.vo;

import java.util.Set;

public class VerificationStatusChecker {
  private static final Set<String> PRESENT = Set.of("출석", "✅", "O");
  private static final Set<String> LATE = Set.of("지각", "⏰");
  private static final Set<String> ABSENT = Set.of("결석", "❌", "X", "인증 없음");

  public static boolean isPresent(String status) {
    return containsAny(status, PRESENT);
  }

  public static boolean isLate(String status) {
    return containsAny(status, LATE);
  }

  public static boolean isAbsent(String status) {
    return containsAny(status, ABSENT);
  }

  public static boolean isAttendanceVerified(String status) {
    return isPresent(status) || isLate(status); // 지각도 출석으로 인정
  }

  public static String convertToEmoji(String status) {
    if (isPresent(status)) return ":white_check_mark:";
    if (isLate(status)) return ":warning:";
    if (isAbsent(status)) return ":x:";
    return ":question:";
  }

  private static boolean containsAny(String status, Set<String> keywords) {
    for (String keyword : keywords) {
      if (status.contains(keyword)) return true;
    }
    return false;
  }
}
