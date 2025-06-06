package shop.genieus.study.commons.provider.model;

import java.time.LocalDate;

public record StampHistoryInfo(
    Long id,
    Long userId,
    LocalDate verifiedAt,
    int ctCount,
    boolean ctVerified,
    int tilCount,
    boolean tilVerified,
    int resumeCount,
    boolean resumeVerified,
    int totalVerifiedCount) {}
