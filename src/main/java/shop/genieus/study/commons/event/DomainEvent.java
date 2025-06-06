package shop.genieus.study.commons.event;

import java.time.LocalDateTime;

public interface DomainEvent<T> {
  DomainEventType getDomainEventType();

  DomainPayload getDomainPayload();

  default LocalDateTime getOccurredAt() {
    return LocalDateTime.now();
  }
}
