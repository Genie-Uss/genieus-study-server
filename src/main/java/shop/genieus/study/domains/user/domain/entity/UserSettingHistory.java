package shop.genieus.study.domains.user.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import org.hibernate.annotations.Comment;
import shop.genieus.study.commons.jpa.BaseEntity;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;

@Entity
@Getter
@Comment("사용자 설정 이력 테이블")
@Table(
    name = "g_user_settings_histories",
    indexes = {
      @Index(name = "idx_user_effective_date", columnList = "userId, effectiveFromDate"),
      @Index(name = "idx_user_active", columnList = "userId, isActive")
    },
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_user_effective_date",
          columnNames = {"userId", "effectiveFromDate"})
    })
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSettingHistory extends BaseEntity {

  @Id
  @Comment("유저 설정 이력 아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("사용자 아이디")
  @Column(nullable = false)
  private Long userId;

  @Comment("적용 시작 날짜")
  @Column(nullable = false)
  private LocalDate effectiveFromDate;

  @Comment("적용 종료 날짜 (null이면 현재까지)")
  private LocalDate effectiveToDate;

  @Comment("희망 출석 시각")
  @Column(nullable = false)
  private LocalTime desiredCheckInTime;

  @Comment("희망 코어 시간 (분)")
  @Column(nullable = false)
  private int desiredCoreTime;

  @Comment("활성 상태")
  @Column(nullable = false)
  private boolean isActive;

  @Comment("참여 상태")
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ParticipationStatus participationStatus;

  public static UserSettingHistory create(
      Long userId,
      LocalDate effectiveFromDate,
      LocalTime desiredCheckInTime,
      int desiredCoreTime,
      ParticipationStatus participationStatus) {

    return UserSettingHistory.builder()
        .userId(userId)
        .effectiveFromDate(effectiveFromDate)
        .desiredCheckInTime(desiredCheckInTime)
        .desiredCoreTime(desiredCoreTime)
        .participationStatus(participationStatus)
        .isActive(true)
        .build();
  }

  public void deactivate(LocalDate effectiveToDate) {
    this.effectiveToDate = effectiveToDate.minusDays(1); // 전날까지 유효
    this.isActive = false;
  }
}
