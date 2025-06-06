package shop.genieus.study.domains.statistics.domain.vo;

import static shop.genieus.study.domains.statistics.domain.vo.VerificationStatusChecker.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import shop.genieus.study.commons.provider.model.AttendanceInfo;
import shop.genieus.study.commons.provider.model.StampHistoryInfo;

@Getter
public class UserStatistics {
  private final Long userId;
  private final String nickname;
  private final List<FinePolicy> fineReasons;
  private final int totalFine;
  private final String fineReason;
  private final String attendanceStatus;
  private final String ctStatus;
  private final String tilStatus;
  private final String resumeStatus;
  private final int totalVerifiedCount;

  public UserStatistics(
      Long userId, String nickname, AttendanceInfo attendance, StampHistoryInfo stampHistory) {
    this.userId = userId;
    this.nickname = nickname;
    this.fineReasons = new ArrayList<>();

    this.attendanceStatus = processAttendance(attendance);

    calculateStampFines(stampHistory);
    this.ctStatus = formatStampStatus(stampHistory.ctVerified(), stampHistory.ctCount());
    this.tilStatus = formatStampStatus(stampHistory.tilVerified(), stampHistory.tilCount());
    this.resumeStatus =
        formatStampStatus(stampHistory.resumeVerified(), stampHistory.resumeCount());

    this.totalVerifiedCount = calculateTotalVerifiedCount(attendanceStatus, stampHistory);

    this.totalFine = fineReasons.stream().mapToInt(FinePolicy::getAmount).sum();
    this.fineReason = getFineReasonText();
  }

  private String processAttendance(AttendanceInfo attendance) {
    if (attendance == null) {
      fineReasons.add(FinePolicy.NO_ATTENDANCE);
      return "`" + KO_ABSENT + "`";
    } else if (attendance.isLate()) {
      fineReasons.add(FinePolicy.LATE_ATTENDANCE);
      return "`" + KO_LATE + "` (" + formatTime(attendance.checkInTime().toLocalTime()) + ")";
    } else {
      return "`" + KO_PRESENT + "` (" + formatTime(attendance.checkInTime().toLocalTime()) + ")";
    }
  }

  private void calculateStampFines(StampHistoryInfo stampHistory) {
    if (!stampHistory.ctVerified()) {
      fineReasons.add(FinePolicy.MISSING_CT);
    }
    if (!stampHistory.tilVerified()) {
      fineReasons.add(FinePolicy.MISSING_TIL);
    }
    if (!stampHistory.resumeVerified()) {
      fineReasons.add(FinePolicy.MISSING_RESUME);
    }
  }

  private int calculateTotalVerifiedCount(String attendanceStatus, StampHistoryInfo stampHistory) {
    int count = 0;

    if (VerificationStatusChecker.isAttendanceVerified(attendanceStatus)) {
      count++;
    }
    if (stampHistory.ctVerified()) {
      count++;
    }
    if (stampHistory.tilVerified()) {
      count++;
    }
    if (stampHistory.resumeVerified()) {
      count++;
    }
    return count;
  }

  private String formatStampStatus(boolean verified, int count) {
    if (count == 0 && !verified) {
      return "`" + KO_NO_VERIFIED + "`";
    }
    String status = verified ? "`" + O_MARK + "`" : "`" + X_MARK + "`";
    return String.format("%s (%d개)", status, count);
  }

  private String formatTime(LocalTime time) {
    if (time == null) return "--:--";
    return time.format(DateTimeFormatter.ofPattern("HH:mm"));
  }

  public String getFineReasonText() {
    if (fineReasons.isEmpty()) {
      return "벌금 없음";
    }
    return fineReasons.stream()
        .map(FinePolicy::getDescription)
        .reduce((a, b) -> a + ", " + b)
        .orElse("");
  }
}
