package shop.genieus.study.domains.stamp.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampSearchInfo;
import shop.genieus.study.domains.stamp.application.dto.result.GetStampSearchResult;
import shop.genieus.study.domains.stamp.application.event.StampViewCreatedEvent;
import shop.genieus.study.domains.stamp.application.event.StampViewDeletedEvent;
import shop.genieus.study.domains.stamp.application.repository.StampViewRepository;
import shop.genieus.study.domains.stamp.domain.entity.StampView;
import shop.genieus.study.domains.stamp.domain.mapper.StampViewMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StampViewService {
  private final StampViewMapper mapper;
  private final StampViewRepository viewRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onStampViewCreated(StampViewCreatedEvent event) {
    StampView view = mapper.from(event);
    StampView saved = viewRepository.save(view);
    log.info(
        "Stamp View 생성: id={}, type={}, userId={}, nickname={}",
        saved.getId(),
        saved.getType(),
        saved.getUserId(),
        saved.getNickname());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onStampViewDeleted(StampViewDeletedEvent event) {
    viewRepository.deleteById(event.id());
    log.info("Stamp View 삭제: id={}", event.id());
  }

  public GetStampSearchResult<StampView> searchStampViews(GetStampSearchInfo info) {
    return viewRepository.findStampViews(info.filterParams(), info.pageable());
  }
}
