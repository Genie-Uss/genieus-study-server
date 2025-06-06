package shop.genieus.study.domains.stamp.application.event.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.genieus.study.domains.stamp.application.StampHistoryService;
import shop.genieus.study.domains.stamp.application.StampViewService;
import shop.genieus.study.domains.stamp.application.event.internal.model.StampCreatedDomainEvent;
import shop.genieus.study.domains.stamp.application.event.internal.model.StampDeletedDomainEvent;
import shop.genieus.study.domains.stamp.domain.event.StampCreatedEvent;
import shop.genieus.study.domains.stamp.domain.event.StampDeletedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class StampInternalEventListener {
  private final StampViewService stampViewService;
  private final StampHistoryService stampHistoryService;

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onStampCreated(StampCreatedEvent event) {
    stampHistoryService.onStampCreated(event);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onStampViewCreated(StampCreatedDomainEvent event) {
    stampViewService.onStampViewCreated(event);
  }

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onStampDeleted(StampDeletedEvent event) {
    stampHistoryService.onStampDeleted(event);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onStampViewDeleted(StampDeletedDomainEvent event) {
    stampViewService.onStampViewDeleted(event);
  }
}
