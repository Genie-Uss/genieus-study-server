package shop.genieus.study.domains.stamp.application.event.external.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.event.DomainEvent;
import shop.genieus.study.commons.event.DomainEventType;
import shop.genieus.study.commons.event.DomainPayload;

@Getter
@RequiredArgsConstructor
public class StampDeletedIntegrationEvent implements DomainEvent {
  private final Long userId;
  private final LocalDateTime verifiedAt;

  public static StampDeletedIntegrationEvent of(Long userId, LocalDateTime verifiedAt) {
    return new StampDeletedIntegrationEvent(userId, verifiedAt);
  }

  @Override
  public DomainEventType getDomainEventType() {
    return DomainEventType.STAMP_DELETED;
  }

  @Override
  public DomainPayload getDomainPayload() {
    return DomainPayload.builder()
        .userId(this.userId)
        .attribute("verifiedAt", this.verifiedAt)
        .build();
  }
}
