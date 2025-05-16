package shop.genieus.study.domains.stamp.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.stamp.domain.event.StampActivityEvent;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Entity
@Getter
@Comment("구직활동 도장 테이블")
@Table(name = "g_resume_stamps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeStamp extends Stamp {

  @Comment("회사/기관명")
  @Column(nullable = false)
  private String companyName;

  @Comment("경력 유형")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CareerType careerType;

  @Comment("활동 유형")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ActivityType activityType;

  @Comment("상세 내용")
  @Column(nullable = false)
  private String description;

  @Comment("관련 URL")
  private String relatedUrl;

  private ResumeStamp(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      String companyName,
      CareerType careerType,
      ActivityType activityType,
      String description,
      String relatedUrl) {
    super(userId, type, verifiedAt);
    this.companyName = companyName;
    this.careerType = careerType;
    this.activityType = activityType;
    this.description = description;
    this.relatedUrl = relatedUrl;
  }

  public static ResumeStamp create(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      String companyName,
      CareerType careerType,
      ActivityType activityType,
      String description,
      String relatedUrl) {
    ResumeStamp stamp =
        new ResumeStamp(
            userId,
            type,
            verifiedAt,
            companyName,
            careerType,
            activityType,
            description,
            relatedUrl);
    stamp.registerEvent(StampActivityEvent.of(stamp));

    return stamp;
  }
}
