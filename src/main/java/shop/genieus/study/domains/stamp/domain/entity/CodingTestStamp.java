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
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Entity
@Getter
@Comment("코딩테스트 도장 테이블")
@Table(name = "g_coding_test_stamps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodingTestStamp extends Stamp {

  @Comment("알고리즘 유형")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AlgorithmType algorithmType;

  @Comment("플랫폼 유형")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PlatformType platformType;

  @Comment("문제 설명")
  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Comment("문제 URL")
  private String problemUrl;

  private CodingTestStamp(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      AlgorithmType algorithmType,
      PlatformType platformType,
      String description,
      String problemUrl) {
    super(userId, type, verifiedAt);
    this.algorithmType = algorithmType;
    this.platformType = platformType;
    this.description = description;
    this.problemUrl = problemUrl;
  }

  public static CodingTestStamp create(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      AlgorithmType algorithmType,
      PlatformType platformType,
      String description,
      String problemUrl) {
    CodingTestStamp stamp =
        new CodingTestStamp(
            userId, type, verifiedAt, algorithmType, platformType, description, problemUrl);
    stamp.registerEvent(StampActivityEvent.of(stamp));

    return stamp;
  }
}
