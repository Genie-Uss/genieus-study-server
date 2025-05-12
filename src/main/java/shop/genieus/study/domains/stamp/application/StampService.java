package shop.genieus.study.domains.stamp.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.stamp.application.dto.info.DeleteStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateCtStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateResumeStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateTilStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetCtStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetResumeStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetTilStampInfo;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCtStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateResumeStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.VerifiedStampResult;
import shop.genieus.study.domains.stamp.application.repository.StampRepository;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.exception.StampBusinessException;
import shop.genieus.study.domains.stamp.domain.policy.StampVerificationPolicy;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {
  private final StampRepository stampRepository;
  private final DateTimeProvider dateTimeProvider;
  private final AttendanceProvider attendanceProvider;

  @Transactional
  public CreateCtStampResult createCodingTestStamp(CreateCtStampInfo info) {
    Long userId = info.userId();
    LocalDateTime verifiedAt = dateTimeProvider.getCurrentDateTime();
    existsByUserIdAndDate(userId, verifiedAt);
    CodingTestStamp codingTestStamp =
        CodingTestStamp.create(
            userId,
            StampType.CT,
            verifiedAt,
            info.algorithmType(),
            info.platformType(),
            info.description(),
            info.problemUrl());
    stampRepository.save(codingTestStamp);
    return CreateCtStampResult.of(codingTestStamp);
  }

  @Transactional
  public CreateTilStampResult createTilStamp(CreateTilStampInfo info) {
    Long userId = info.userId();
    LocalDateTime verifiedAt = dateTimeProvider.getCurrentDateTime();
    existsByUserIdAndDate(userId, verifiedAt);
    TilStamp tilStamp =
        TilStamp.create(
            userId,
            StampType.TIL,
            verifiedAt,
            info.title(),
            info.categoryType(),
            info.content(),
            info.relatedUrl());
    stampRepository.save(tilStamp);
    return CreateTilStampResult.of(tilStamp);
  }

  @Transactional
  public CreateResumeStampResult createJobActivityStamp(CreateResumeStampInfo info) {
    Long userId = info.userId();
    LocalDateTime verifiedAt = dateTimeProvider.getCurrentDateTime();
    existsByUserIdAndDate(userId, verifiedAt);
    ResumeStamp resumeStamp =
        ResumeStamp.create(
            userId,
            StampType.RESUME,
            verifiedAt,
            info.companyName(),
            info.careerType(),
            info.activityType(),
            info.description(),
            info.relatedUrl());
    stampRepository.save(resumeStamp);
    return CreateResumeStampResult.of(resumeStamp);
  }

  @Transactional(readOnly = true)
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

  @Transactional(readOnly = true)
  public List<CodingTestStamp> getCtStampByDate(GetCtStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return stampRepository.getCtStampByDate(userId, date);
  }

  @Transactional(readOnly = true)
  public List<TilStamp> getTilStampByDate(GetTilStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return stampRepository.getTilStampByDate(userId, date);
  }

  @Transactional(readOnly = true)
  public List<ResumeStamp> getResumeStampByDate(GetResumeStampInfo info) {
    Long userId = info.userId();
    LocalDate date = getCurrentDate(info.date());
    return stampRepository.getResumeStampByDate(userId, date);
  }

  @Transactional
  public void deleteStamp(DeleteStampInfo info) {
    Long userId = info.userId();
    Long stampId = info.stampId();
    Stamp stamp = stampRepository.findById(stampId);
    stamp.delete(userId);
    stampRepository.deleteById(stampId);
  }

  private void existsByUserIdAndDate(Long userId, LocalDateTime currentTime) {
    if (attendanceProvider.existsByUserIdAndDate(userId, currentTime.toLocalDate())) {
      return;
    }
    throw StampBusinessException.mustCheckInBeforeStamping();
  }

  private LocalDate getCurrentDate(LocalDate date) {
    if (date == null) {
      date = dateTimeProvider.getCurrentDate();
    }
    return date;
  }
}
