package shop.genieus.study.commons.provider.model;

import java.time.LocalTime;

public record UserInfo(
    Long id,
    String email,
    String nickname,
    String profileImage,
    String roleName,
    LocalTime desiredCheckInTime,
    Integer desiredCoreTime,
    Boolean isActive,
    String participationStatus) {}
