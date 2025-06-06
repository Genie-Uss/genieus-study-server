package shop.genieus.study.commons.event;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class EventFilterAspect {

  @Around("@annotation(eventFilter)")
  public Object filterEvent(ProceedingJoinPoint joinPoint, EventFilter eventFilter)
      throws Throwable {
    Object[] args = joinPoint.getArgs();

    DomainEvent event =
        Arrays.stream(args)
            .filter(DomainEvent.class::isInstance)
            .map(DomainEvent.class::cast)
            .findFirst()
            .orElse(null);

    if (event == null) {
      log.warn("메소드 파라미터에서 DomainEvent를 찾을 수 없습니다.");
      return null;
    }

    if (eventFilter.includeAll()
        || Arrays.asList(eventFilter.value()).contains(event.getDomainEventType())) {

      log.info(
          "이벤트 처리: {} - 메소드: {}", event.getDomainEventType(), joinPoint.getSignature().getName());
      return joinPoint.proceed();
    } else {
      log.debug(
          "이벤트 필터링됨: {} - 메소드: {}", event.getDomainEventType(), joinPoint.getSignature().getName());
      return null;
    }
  }
}
