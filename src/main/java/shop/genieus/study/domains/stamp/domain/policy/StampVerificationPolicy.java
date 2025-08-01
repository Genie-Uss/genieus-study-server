package shop.genieus.study.domains.stamp.domain.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StampVerificationPolicy {
    CT(1),
    TIL(1),
    RESUME(1);

    private final int minCount;

    public boolean isVerified(long count) {
        return count >= minCount;
    }
}
