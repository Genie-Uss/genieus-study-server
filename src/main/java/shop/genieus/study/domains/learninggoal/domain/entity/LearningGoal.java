package shop.genieus.study.domains.learninggoal.domain.entity;

import static org.springframework.util.StringUtils.hasText;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.genieus.study.domains.learninggoal.domain.exception.LearningGoalValidationException;

@Entity
@Getter
@Comment("학습 목표 테이블")
@Table(
    name = "g_learning_goals",
    indexes = {
      @Index(name = "idx_user_date", columnList = "userId, date"),
      @Index(name = "idx_user_completed", columnList = "userId, isCompleted")
    })
@Builder(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningGoal {

  private static final int MAX_CONTENT_LENGTH = 50;

  @Id
  @Comment("학습 목표 아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("사용자 아이디")
  @Column(nullable = false)
  private Long userId;

  @Comment("목표 날짜")
  @Column(nullable = false)
  private LocalDate date;

  @Comment("목표 내용")
  @Column(nullable = false, length = MAX_CONTENT_LENGTH)
  private String content;

  @Builder.Default
  @Comment("완료 여부")
  @Column(nullable = false)
  private Boolean isCompleted = false;

  @CreatedDate
  @Comment("생성 일시")
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public static LearningGoal create(
      Long userId, LocalDate date, String content, LocalDate currentDate) {
    validateDate(date, currentDate);
    validateContent(content);

    return LearningGoal.builder()
        .userId(userId)
        .date(date)
        .content(content)
        .isCompleted(false)
        .build();
  }

  private static void validateDate(LocalDate date, LocalDate currentDate) {
    if (date == null) {
      throw LearningGoalValidationException.requiredDate();
    }

    if (!date.equals(currentDate)) {
      throw LearningGoalValidationException.invalidDate();
    }
  }

  private static void validateContent(String content) {
    if (!hasText(content)) {
      throw LearningGoalValidationException.requiredContent();
    }

    if (content.length() > MAX_CONTENT_LENGTH) {
      throw LearningGoalValidationException.exceedsContentMaxLength(MAX_CONTENT_LENGTH);
    }
  }

  public void toggleCompletion() {
    this.isCompleted = !this.isCompleted;
  }

  public boolean isOwnedBy(Long userId) {
    return this.userId.equals(userId);
  }

  public boolean isCompleted() {
    return this.isCompleted;
  }
}
