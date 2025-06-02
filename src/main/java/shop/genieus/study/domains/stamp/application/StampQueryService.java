package shop.genieus.study.domains.stamp.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetCtStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetResumeStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetTilStampInfo;
import shop.genieus.study.domains.stamp.application.dto.result.VerifiedStampResult;
import shop.genieus.study.domains.stamp.application.repository.StampRepository;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.policy.StampVerificationPolicy;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StampQueryService {
  private final StampRepository stampRepository;
  private final DateTimeProvider dateTimeProvider;

  public List<VerifiedStampResult> getVerifiedStampByDate(GetStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());

    Map<StampType, Long> countMap =
        Map.of(
            StampType.CT, stampRepository.countCtStampByDate(userId, date),
            StampType.TIL, stampRepository.countTilStampByDate(userId, date),
            StampType.RESUME, stampRepository.countResumeStampByDate(userId, date));

    return countMap.entrySet().stream()
        .map(
            entry -> {
              StampType type = entry.getKey();
              long count = entry.getValue();
              boolean isVerified = StampVerificationPolicy.valueOf(type.name()).isVerified(count);
              return new VerifiedStampResult(type.name(), isVerified);
            })
        .toList();
  }

  public List<CodingTestStamp> getCtStampByDate(GetCtStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return stampRepository.getCtStampByDate(userId, date);
  }

  public List<TilStamp> getTilStampByDate(GetTilStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return stampRepository.getTilStampByDate(userId, date);
  }

  public List<ResumeStamp> getResumeStampByDate(GetResumeStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return stampRepository.getResumeStampByDate(userId, date);
  }

  public Stamp getStampDetail(Long stampId) {
    return stampRepository.findById(stampId);
  }

  private LocalDate getCurrentDate(LocalDate date) {
    return date != null ? date : dateTimeProvider.getCurrentDate();
  }
}
