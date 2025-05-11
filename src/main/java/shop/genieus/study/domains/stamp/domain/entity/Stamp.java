package shop.genieus.study.domains.stamp.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.stamp.domain.exception.StampBusinessException;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Entity
@Getter
@SuperBuilder
@Comment("인증 도장 테이블")
@Table(name = "g_stamps")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stamp {
  @Id
  @Comment("인증 도장 아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Comment("유저 아이디")
  protected Long userId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Comment("스탬프 유형")
  protected StampType type;

  @Comment("인증 시간")
  protected LocalDateTime verifiedAt;

  public static Stamp create(Long userId, StampType type, LocalDateTime verifiedAt) {
    return Stamp.builder().userId(userId).type(type).verifiedAt(verifiedAt).build();
  }

  public void validate(Long userId) {
    if (this.userId != null && !this.userId.equals(userId)) {
      throw StampBusinessException.noPermissionForStamp();
    }
  }
}
