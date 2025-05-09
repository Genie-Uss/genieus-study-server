package shop.genieus.study.domains.stamp.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Entity
@Getter
@SuperBuilder
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
  @Column(nullable = false)
  private String content;

  @Comment("관련 URL")
  private String relatedUrl;

  public static TilStamp create(
      Long userId,
      StampType type,
      LocalDateTime verifiedAt,
      String title,
      CategoryType categoryType,
      String content,
      String relatedUrl) {
    return TilStamp.builder()
        .userId(userId)
        .type(type)
        .verifiedAt(verifiedAt)
        .title(title)
        .categoryType(categoryType)
        .content(content)
        .relatedUrl(relatedUrl)
        .build();
  }
}
