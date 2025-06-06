package shop.genieus.study.domains.stamp.application.event.internal.model;

public record StampDeletedDomainEvent(Long id) {
  public static StampDeletedDomainEvent of(Long stampId) {
    return new StampDeletedDomainEvent(stampId);
  }
}
