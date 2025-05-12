package shop.genieus.study.domains.stamp.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.stamp.domain.vo.StampType;
import shop.genieus.study.domains.stamp.domain.vo.VerificationStatus;

@Entity
@Getter
@Comment("도장 기록 테이블")
@Table(name = "g_stamp_histories")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StampHistory {
  @Id
  @Comment("도장 기록 아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Comment("유저 아이디")
  private Long userId;

  @Comment("기록 날짜")
  private LocalDate verifiedAt;

  @Embedded private VerificationStatus verificationStatus;

  public static StampHistory create(Long userId, LocalDate verifiedAt) {
    return StampHistory.builder().userId(userId).verifiedAt(verifiedAt).verificationStatus(new VerificationStatus()).build();
  }

  public void addStamp(StampType type) {
    verificationStatus.incrementCount(type);
  }

  public void removeStamp(StampType type) {
    verificationStatus.decrementCount(type);
  }
}
