package shop.genieus.study.domains.attendance.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyResult {
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
