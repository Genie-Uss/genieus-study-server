package shop.genieus.study.domains.stamp.application;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.stamp.application.dto.info.DeleteStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.StampCreateInfo;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateCtStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateResumeStampInfo;
import shop.genieus.study.domains.stamp.application.dto.info.create.CreateTilStampInfo;
import shop.genieus.study.domains.stamp.application.dto.result.CreateCtStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateResumeStampResult;
import shop.genieus.study.domains.stamp.application.dto.result.CreateTilStampResult;
import shop.genieus.study.domains.stamp.application.event.StampViewCreatedEvent;
import shop.genieus.study.domains.stamp.application.event.StampViewDeletedEvent;
import shop.genieus.study.domains.stamp.application.repository.StampRepository;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.event.StampActivityEvent;
import shop.genieus.study.domains.stamp.domain.exception.StampBusinessException;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StampCommandService {
  private final StampRepository stampRepository;
  private final AttendanceProvider attendanceProvider;
  private final DateTimeProvider dateTimeProvider;
  private final ApplicationEventPublisher publisher;

  public CreateCtStampResult createCodingTestStamp(CreateCtStampInfo info) {
    return createStamp(
        info,
        StampType.CT,
        (userId, verifiedAt) ->
            CodingTestStamp.create(
                userId,
                StampType.CT,
                verifiedAt,
                info.algorithmType(),
                info.platformType(),
                info.description(),
                info.problemUrl()),
        CreateCtStampResult::of,
        (stamp, nickname) -> StampViewCreatedEvent.ofCodingTest(stamp, nickname));
  }

  public CreateTilStampResult createTilStamp(CreateTilStampInfo info) {
    return createStamp(
        info,
        StampType.TIL,
        (userId, verifiedAt) ->
            TilStamp.create(
                userId,
                StampType.TIL,
                verifiedAt,
                info.title(),
                info.categoryType(),
                info.content(),
                info.relatedUrl()),
        CreateTilStampResult::of,
        (stamp, nickname) -> StampViewCreatedEvent.ofTil(stamp, nickname));
  }

  public CreateResumeStampResult createJobActivityStamp(CreateResumeStampInfo info) {
    return createStamp(
        info,
        StampType.RESUME,
        (userId, verifiedAt) ->
            ResumeStamp.create(
                userId,
                StampType.RESUME,
                verifiedAt,
                info.title(),
                info.careerType(),
                info.activityType(),
                info.description(),
                info.relatedUrl()),
        CreateResumeStampResult::of,
        (stamp, nickname) -> StampViewCreatedEvent.ofResume(stamp, nickname));
  }

  public void deleteStamp(DeleteStampInfo info) {
    Long userId = info.userId();
    Long stampId = info.stampId();

    Stamp stamp = stampRepository.findById(stampId);
    stamp.delete(userId);
    stampRepository.delete(stamp);

    publisher.publishEvent(StampViewDeletedEvent.of(stampId));

    log.info("도장 삭제 완료: userId={}, stampId={}", userId, stampId);
  }

  private <I extends StampCreateInfo, S extends Stamp, R> R createStamp(
      I info,
      StampType stampType,
      BiFunction<Long, LocalDateTime, S> creator,
      Function<S, R> resultFactory,
      BiFunction<S, String, StampViewCreatedEvent> eventFactory) {

    Long userId = info.getUserId();
    String nickname = info.getNickname();
    LocalDateTime verifiedAt = dateTimeProvider.getCurrentDateTime();

    validateAttendance(userId, verifiedAt);

    S stamp = creator.apply(userId, verifiedAt);
    S savedStamp = (S) stampRepository.save(stamp);

    publisher.publishEvent(StampActivityEvent.of(savedStamp));
    publisher.publishEvent(eventFactory.apply(savedStamp, nickname));

    log.info("도장 생성 완료: type={}, userId={}, stampId={}", stampType, userId, savedStamp.getId());

    return resultFactory.apply(savedStamp);
  }

  private void validateAttendance(Long userId, LocalDateTime currentTime) {
    if (!attendanceProvider.existsByUserIdAndDate(userId, currentTime.toLocalDate())) {
      throw StampBusinessException.mustCheckInBeforeStamping();
    }
  }
}
