package shop.genieus.study.domains.stamp.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.commons.provider.StampHistoryProvider;
import shop.genieus.study.commons.provider.model.StampHistoryInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampInfo;
import shop.genieus.study.domains.stamp.application.mapper.StampHistoryMapper;
import shop.genieus.study.domains.stamp.application.repository.StampHistoryRepository;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;
import shop.genieus.study.domains.stamp.domain.event.StampCreatedEvent;
import shop.genieus.study.domains.stamp.domain.event.StampDeletedEvent;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampHistoryService implements StampHistoryProvider {
  private final DateTimeProvider dateTimeProvider;
  private final StampHistoryRepository stampHistoryRepository;
  private final StampHistoryMapper mapper;

  @Transactional
  public void onStampCreated(StampCreatedEvent event) {
    Long userId = event.userId();
    LocalDate verifiedAt = event.verifiedAt().toLocalDate();
    StampType type = event.stampType();
    StampHistory stampHistory = findByUserIdAndVerifiedAt(userId, verifiedAt);
    stampHistory.addStamp(type);
    stampHistoryRepository.save(stampHistory);
  }

  @Transactional
  public void onStampDeleted(StampDeletedEvent event) {
    Long userId = event.userId();
    LocalDate verifiedAt = event.verifiedAt().toLocalDate();
    StampType type = event.stampType();
    StampHistory stampHistory = findByUserIdAndVerifiedAt(userId, verifiedAt);
    stampHistory.removeStamp(type);
  }

  @Transactional(readOnly = true)
  public StampHistory getStampHistoryByDate(GetStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return findByUserIdAndVerifiedAt(userId, date);
  }

  @Override
  @Transactional(readOnly = true)
  public Map<Long, StampHistoryInfo> getStampHistories(List<Long> userIds, LocalDate date) {
    List<StampHistory> existingHistories =
        stampHistoryRepository.findByUserIdsAndVerifiedAt(userIds, date);

    return existingHistories.stream()
        .collect(Collectors.toMap(sh -> sh.getUserId(), sh -> mapper.from(sh)));
  }

  private StampHistory findByUserIdAndVerifiedAt(Long userId, LocalDate verifiedAt) {
    StampHistory existingHistory =
        stampHistoryRepository.findByUserIdAndVerifiedAt(userId, verifiedAt);
    if (existingHistory == null) {
      return StampHistory.create(userId, verifiedAt);
    }
    return existingHistory;
  }

  private LocalDate getCurrentDate(LocalDate date) {
    if (date == null) {
      date = dateTimeProvider.getCurrentDate();
    }
    return date;
  }
}
