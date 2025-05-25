package shop.genieus.study.domains.user.application.dto.info;

import java.time.LocalTime;

public record UpdateUserSettingInfo(Long userId, LocalTime newCheckInTime, int newCoreTime) {}
