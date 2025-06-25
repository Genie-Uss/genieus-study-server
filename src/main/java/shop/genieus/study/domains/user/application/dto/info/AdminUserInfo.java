package shop.genieus.study.domains.user.application.dto.info;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;

public record AdminUserInfo(
    Long id,
    String nickname,
    String email,
    String status,
    ParticipationStatus participationStatus,
    String role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  @QueryProjection
  public AdminUserInfo(
      Long id,
      String nickname,
      String email,
      String status,
      ParticipationStatus participationStatus,
      String role,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
    this.status = status;
    this.participationStatus = participationStatus;
    this.role = role;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static AdminUserInfo from(User user) {
    return new AdminUserInfo(
        user.getId(),
        user.getNickname().getValue(),
        user.getEmail().getValue(),
        user.getStatus().name(),
        user.getParticipationStatus(),
        user.getRole().name(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }
}
