package shop.genieus.study.domains.stamp.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Embeddable
public class VerificationStatus {
  private static final int CT_MIN_COUNT = 2;
  private static final int TIL_MIN_COUNT = 1;
  private static final int RESUME_MIN_COUNT = 1;

  @Comment("Ct 인증 수")
  private int ctCount;

  @Comment("Ct 인증 상태")
  private boolean isCtVerified;

  @Comment("Til 인증 수")
  private int tilCount;

  @Comment("Til 인증 상태")
  private boolean isTilVerified;

  @Comment("Resume 인증 수")
  private int resumeCount;

  @Comment("Resume 인증 상태")
  private boolean isResumeVerified;

  @Comment("총 인증된 타입 수")
  private int totalVerifiedCount;
  
  public void incrementCount(StampType type) {
    updateCount(type, 1);
  }

  public void decrementCount(StampType type) {
    updateCount(type, -1);
  }

  private void updateCount(StampType type, int delta) {
    switch (type) {
      case CT:
        updateCtCount(delta);
        break;
      case TIL:
        updateTilCount(delta);
        break;
      case RESUME:
        updateResumeCount(delta);
        break;
    }
  }

  private void updateCtCount(int delta) {
    ctCount = Math.max(0, ctCount + delta);
    boolean wasVerified = isCtVerified;
    isCtVerified = ctCount >= CT_MIN_COUNT;
    updateTotal(wasVerified, isCtVerified);
  }

  private void updateTilCount(int delta) {
    tilCount = Math.max(0, tilCount + delta);
    boolean wasVerified = isTilVerified;
    isTilVerified = tilCount >= TIL_MIN_COUNT;
    updateTotal(wasVerified, isTilVerified);
  }

  private void updateResumeCount(int delta) {
    resumeCount = Math.max(0, resumeCount + delta);
    boolean wasVerified = isResumeVerified;
    isResumeVerified = resumeCount >= RESUME_MIN_COUNT;
    updateTotal(wasVerified, isResumeVerified);
  }

  private void updateTotal(boolean before, boolean after) {
    if (!before && after) totalVerifiedCount++;
    else if (before && !after) totalVerifiedCount--;
  }
}
