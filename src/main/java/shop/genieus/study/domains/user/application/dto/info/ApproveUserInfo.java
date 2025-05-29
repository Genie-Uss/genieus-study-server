package shop.genieus.study.domains.user.application.dto.info;


public record ApproveUserInfo(Long adminUserId, Long targetUserId, Boolean isParticipating) {}
