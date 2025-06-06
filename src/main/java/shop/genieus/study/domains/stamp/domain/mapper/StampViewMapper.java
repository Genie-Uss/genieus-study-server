package shop.genieus.study.domains.stamp.domain.mapper;

import org.springframework.stereotype.Component;
import shop.genieus.study.domains.stamp.application.event.internal.model.StampCreatedDomainEvent;
import shop.genieus.study.domains.stamp.domain.entity.StampView;

@Component
public class StampViewMapper {
  public StampView from(StampCreatedDomainEvent event) {
    return switch (event.type()) {
      case CT ->
          StampView.ct(
              event.id(),
              event.type(),
              event.userId(),
              event.nickname(),
              event.verifiedAt(),
              event.algorithmType(),
              event.platformType());
      case TIL ->
          StampView.til(
              event.id(),
              event.type(),
              event.title(),
              event.userId(),
              event.nickname(),
              event.verifiedAt(),
              event.categoryType());
      case RESUME ->
          StampView.resume(
              event.id(),
              event.type(),
              event.title(),
              event.userId(),
              event.nickname(),
              event.verifiedAt(),
              event.careerType(),
              event.activityType());
    };
  }
}
