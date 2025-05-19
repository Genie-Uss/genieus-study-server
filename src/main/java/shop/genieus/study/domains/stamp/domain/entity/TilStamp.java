package shop.genieus.study.domains.stamp.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.stamp.domain.event.StampActivityEvent;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Entity
@Getter
@Comment("TIL 도장 테이블")
@Table(name = "g_til_stamps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TilStamp extends Stamp {

  @Comment("제목")
  @Column(nullable = false)
  private String title;

  @Comment("카테고리 유형")
  @Column(nullable = false)
  private CategoryType categoryType;

  @Comment("상세 내용")
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Comment("관련 URL")
  private String relatedUrl;

  private TilStamp(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      String title,
      CategoryType categoryType,
      String content,
      String relatedUrl) {
    super(userId, type, verifiedAt);
    this.title = title;
    this.categoryType = categoryType;
    this.content = content;
    this.relatedUrl = relatedUrl;
  }

  public static TilStamp create(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      String title,
      CategoryType categoryType,
      String content,
      String relatedUrl) {
    TilStamp stamp =
        new TilStamp(userId, type, verifiedAt, title, categoryType, content, relatedUrl);
    stamp.registerEvent(StampActivityEvent.of(stamp));

    return stamp;
  }
}
