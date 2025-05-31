package shop.genieus.study.domains.stamp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.stamp.domain.vo.*;

@Entity
@Getter
@Comment("인증 도장 뷰 테이블")
@Table(name = "g_stamp_views")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StampView {
  @Id protected Long id;

  @Comment("스탬프 유형")
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  protected StampType type;

  @Comment("제목")
  @Column(nullable = false)
  protected String title;

  @Comment("유저 아이디")
  @Column(nullable = false)
  protected Long userId;

  @Comment("유저 닉네임")
  @Column(nullable = false)
  protected String nickname;

  @Comment("인증 시간")
  @Column(nullable = false)
  protected LocalDateTime verifiedAt;

  @JsonIgnore
  @Comment("resume 경력 유형")
  private String careerType;

  @JsonIgnore
  @Comment("resume 활동 유형")
  private String activityType;

  @JsonIgnore
  @Comment("ct 알고리즘 유형")
  private String algorithmType;

  @JsonIgnore
  @Comment("ct 플랫폼 유형")
  private String platformType;

  @JsonIgnore
  @Comment("til 카테고리 유형")
  private String categoryType;

  public static StampView ct(
      Long id,
      StampType type,
      Long userId,
      String nickname,
      LocalDateTime verifiedAt,
      AlgorithmType algorithm,
      PlatformType platform) {

    return base(
        id,
        type,
        "[" + platform.getFieldName() + "] " + algorithm.getFieldName(),
        userId,
        nickname,
        verifiedAt,
        null,
        null,
        algorithm.getFieldName(),
        platform.getFieldName(),
        null);
  }

  public static StampView til(
      Long id,
      StampType type,
      String title,
      Long userId,
      String nickname,
      LocalDateTime verifiedAt,
      CategoryType category) {

    return base(
        id,
        type,
        title,
        userId,
        nickname,
        verifiedAt,
        null,
        null,
        null,
        null,
        category.getFieldName());
  }

  public static StampView resume(
      Long id,
      StampType type,
      String title,
      Long userId,
      String nickname,
      LocalDateTime verifiedAt,
      CareerType career,
      ActivityType activity) {

    return base(
        id,
        type,
        title,
        userId,
        nickname,
        verifiedAt,
        career.getFieldName(),
        activity.getFieldName(),
        null,
        null,
        null);
  }

  private static StampView base(
      Long id,
      StampType type,
      String title,
      Long userId,
      String nickname,
      LocalDateTime verifiedAt,
      String careerType,
      String activityType,
      String algorithmType,
      String platformType,
      String categoryType) {

    return new StampView(
        id,
        type,
        title,
        userId,
        nickname,
        verifiedAt,
        careerType,
        activityType,
        algorithmType,
        platformType,
        categoryType);
  }

  public String[] getCategories() {
    return Stream.of(careerType, activityType, algorithmType, platformType, categoryType)
        .filter(Objects::nonNull)
        .toArray(String[]::new);
  }
}
