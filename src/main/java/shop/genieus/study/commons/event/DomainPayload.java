package shop.genieus.study.commons.event;

import java.util.HashMap;
import java.util.Map;
import lombok.*;
import lombok.experimental.Accessors;

public record DomainPayload(Long domainId, Long userId, Map<String, Object> attributes) {
  public DomainPayload {
    attributes = attributes != null ? Map.copyOf(attributes) : Map.of();
  }

  public static DomainPayloadBuilder builder() {
    return new DomainPayloadBuilder();
  }

  @SuppressWarnings("unchecked")
  public <T> T attribute(String key, Class<T> type) {
    Object value = attributes.get(key);

    if (value == null) {
      throw new IllegalArgumentException("속성을 찾을 수 없습니다: " + key);
    }

    if (!type.isInstance(value)) {
      throw new ClassCastException(
          String.format(
              "속성 '%s'을(를) %s 타입으로 변환할 수 없습니다. 실제 타입: %s",
              key, type.getSimpleName(), value.getClass().getSimpleName()));
    }

    return (T) value;
  }

  @Setter
  @Accessors(fluent = true, chain = true)
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class DomainPayloadBuilder {
    private final Map<String, Object> attributes = new HashMap<>();
    private Long domainId;
    private Long userId;

    public DomainPayloadBuilder attribute(String key, Object value) {
      this.attributes.put(key, value);
      return this;
    }

    public DomainPayload build() {
      return new DomainPayload(domainId, userId, attributes);
    }
  }
}
