package shop.genieus.study.domains.stamp.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.genieus.study.domains.stamp.application.event.StampViewCreatedEvent;
import shop.genieus.study.domains.stamp.application.event.StampViewDeletedEvent;
import shop.genieus.study.domains.stamp.domain.event.StampCreatedEvent;
import shop.genieus.study.domains.stamp.domain.event.StampDeletedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class StampEventListener {
  private final StampViewService stampViewService;
  private final StampHistoryService stampHistoryService;

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onStampCreated(StampCreatedEvent event) {
    stampHistoryService.onStampCreated(event);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onStampViewCreated(StampViewCreatedEvent event) {
    stampViewService.onStampViewCreated(event);
  }

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onStampDeleted(StampDeletedEvent event) {
    stampHistoryService.onStampDeleted(event);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onStampViewDeleted(StampViewDeletedEvent event) {
    stampViewService.onStampViewDeleted(event);
  }
}
