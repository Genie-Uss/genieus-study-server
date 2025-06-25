package shop.genieus.study.domains.user.application;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.domains.user.application.repository.UserSettingHistoryRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;
import shop.genieus.study.domains.user.domain.vo.UserSettings;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserSettingHistoryCommandService {
  private final UserSettingHistoryRepository settingHistoryRepository;

  public void createInitialSettingHistory(User user, LocalDate today) {
    UserSettings settings = user.getCurrentSettings();
    createNewSettingHistory(
        user.getId(),
        today,
        settings.getDesiredCheckInTime(),
        settings.getDesiredCoreTime(),
        settings.getParticipationStatus(),
        "신규 회원 가입");

    log.info("초기 설정 이력 생성: userId={}", user.getId());
  }

  public void updateSettingHistory(User user, LocalDate effectiveDate, String reason) {
    Long userId = user.getId();
    deactivateCurrentHistory(userId, effectiveDate);

    UserSettings settings = user.getCurrentSettings();
    createNewSettingHistory(
        userId,
        effectiveDate,
        settings.getDesiredCheckInTime(),
        settings.getDesiredCoreTime(),
        settings.getParticipationStatus(),
        reason);
  }

  private void deactivateCurrentHistory(Long userId, LocalDate effectiveDate) {
    UserSettingHistory history = settingHistoryRepository.findCurrentActiveSettings(userId);
    history.deactivate(effectiveDate);
    settingHistoryRepository.save(history);

    log.info("기존 설정 이력 비활성화: userId={}, historyId={}", userId, history.getId());
  }

  private void createNewSettingHistory(
      Long userId,
      LocalDate effectiveDate,
      LocalTime checkInTime,
      int coreTime,
      ParticipationStatus participationStatus,
      String reason) {

    UserSettingHistory newHistory =
        UserSettingHistory.create(
            userId, effectiveDate, checkInTime, coreTime, participationStatus, reason);
    settingHistoryRepository.save(newHistory);

    log.info("새 설정 이력 생성: userId={}, effectiveFromDate={}", userId, effectiveDate);
  }
}
