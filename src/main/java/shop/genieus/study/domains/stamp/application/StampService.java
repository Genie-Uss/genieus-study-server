package shop.genieus.study.domains.stamp.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.exception.ApiBusinessException;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.domains.stamp.application.dto.info.CreateCodingTestStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.CreateJobActivityStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.CreateTilStampInfo;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCodingTestStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateJobActivityStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.application.repository.StampRepository;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.JobActivityStamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {
  private final StampRepository stampRepository;
  private final AttendanceProvider attendanceProvider;

  @Transactional
  public CreateCodingTestStampResult createCodingTestStamp(CreateCodingTestStampInfo info) {
    Long userId = info.userId();
    LocalDateTime verifiedAt = getCurrentDateTime();
    existsByUserIdAndDate(userId, verifiedAt);
    CodingTestStamp codingTestStamp =
        CodingTestStamp.create(
            userId,
            StampType.CODING_TEST,
            verifiedAt,
            info.algorithmType(),
            info.platformType(),
            info.description(),
            info.problemUrl());
    stampRepository.save(codingTestStamp);
    return CreateCodingTestStampResult.of(codingTestStamp);
  }

  @Transactional
  public CreateTilStampResult createTilStamp(CreateTilStampInfo info) {
    Long userId = info.userId();
    LocalDateTime verifiedAt = getCurrentDateTime();
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
  public CreateJobActivityStampResult createJobActivityStamp(CreateJobActivityStampInfo info) {
    Long userId = info.userId();
    LocalDateTime verifiedAt = getCurrentDateTime();
    existsByUserIdAndDate(userId, verifiedAt);
    JobActivityStamp jobActivityStamp =
        JobActivityStamp.create(
            userId,
            StampType.JOB_ACTIVITY,
            verifiedAt,
            info.companyName(),
            info.careerType(),
            info.activityType(),
            info.description(),
            info.relatedUrl());
    stampRepository.save(jobActivityStamp);
    return CreateJobActivityStampResult.of(jobActivityStamp);
  }

  private void existsByUserIdAndDate(Long userId, LocalDateTime currentTime) {
    if (attendanceProvider.existsByUserIdAndDate(userId, currentTime.toLocalDate())) {
      return;
    }
    throw new ApiBusinessException("출석 이후 도장을 찍을 수 있습니다.", Domain.STAMP);
  }

  private LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now();
  }
}
