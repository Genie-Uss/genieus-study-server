package shop.genieus.study.commons.provider.dto;

import java.time.LocalTime;

public record UserInfo(
    Long id,
    String email,
    String nickname,
    String profileImage,
    String roleName,
    LocalTime desiredCheckInTime,
    Integer desiredCoreTime,
    Boolean isActive) {}
