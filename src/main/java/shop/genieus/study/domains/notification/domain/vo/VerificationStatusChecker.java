package shop.genieus.study.domains.notification.domain.vo;

import java.util.Set;

public class VerificationStatusChecker {
  public static final String KO_PRESENT = "출석";
  public static final String KO_LATE = "지각";
  public static final String KO_ABSENT = "결석";
  public static final String KO_NO_VERIFIED = "인증 없음";
  public static final String O_MARK = "O";
  public static final String X_MARK = "X";

  private static final Set<String> PRESENT = Set.of(KO_PRESENT, O_MARK);
  private static final Set<String> LATE = Set.of(KO_LATE);
  private static final Set<String> ABSENT = Set.of(KO_ABSENT, X_MARK, KO_NO_VERIFIED);

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
    return isPresent(status);
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
