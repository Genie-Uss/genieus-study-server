package shop.genieus.study.domains.attendance.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.attendance.domain.exception.AttendanceValidationException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyResult {

  private static final int MIN_ACTUAL_STUDY_MINUTES = 0;
  private static final int MIN_TARGET_STUDY_MINUTES = 1;
  private static final double MAX_ACHIEVEMENT_RATE = 100.0;

  @Comment("총 공부 시간(분)")
  @Column(name = "study_minutes")
  private int studyMinutes;

  @Comment("목표 달성률")
  @Column(name = "achievement_rate")
  private double achievementRate;

  private StudyResult(int studyMinutes, double achievementRate) {
    this.studyMinutes = studyMinutes;
    this.achievementRate = achievementRate;
  }

  public static StudyResult initial() {
    return new StudyResult(0, 0.0);
  }

  public static StudyResult calculate(int actualStudyMinutes, int desiredCoreTime) {
    validateStudyMinutes(actualStudyMinutes, desiredCoreTime);
    double achievementRate = calculateAchievementRate(actualStudyMinutes, desiredCoreTime);
    return new StudyResult(actualStudyMinutes, achievementRate);
  }

  private static void validateStudyMinutes(int actual, int target) {
    if (actual < MIN_ACTUAL_STUDY_MINUTES) {
      throw AttendanceValidationException.invalidActualStudyMinutes(MIN_ACTUAL_STUDY_MINUTES);
    }
    if (target < MIN_TARGET_STUDY_MINUTES) {
      throw AttendanceValidationException.invalidTargetStudyMinutes(MIN_TARGET_STUDY_MINUTES);
    }
  }

  private static double calculateAchievementRate(int actual, int target) {
    double rate = (double) actual / target * 100.0;
    return Math.min(MAX_ACHIEVEMENT_RATE, rate);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof StudyResult)) return false;
    StudyResult that = (StudyResult) o;
    return studyMinutes == that.studyMinutes
        && Double.compare(that.achievementRate, achievementRate) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(studyMinutes, achievementRate);
  }
}
