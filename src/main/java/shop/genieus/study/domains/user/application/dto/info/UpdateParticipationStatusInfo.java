package shop.genieus.study.domains.user.application.dto.info;

import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;

public record UpdateParticipationStatusInfo(
    Long adminUserId, Long targetUserId, ParticipationStatus participationStatus) {}
