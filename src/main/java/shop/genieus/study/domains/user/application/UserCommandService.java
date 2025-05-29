package shop.genieus.study.domains.user.application;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.user.application.dto.info.SignupUserInfo;
import shop.genieus.study.domains.user.application.dto.info.UpdateUserSettingInfo;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.application.repository.UserSettingHistoryRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;
import shop.genieus.study.domains.user.domain.vo.UserSettings;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserCommandService {
  private final UserRepository repository;
  private final UserSettingHistoryRepository settingHistoryRepository;
  private final PasswordEncryptionService encryptionService;
  private final DateTimeProvider dateTimeProvider;

  public User signupUser(SignupUserInfo info) {
    isSamePasswordAndPasswordConfirm(info);
    isEmailAlreadyRegistered(info);

    User saved =
        repository.save(
            User.create(info.email(), info.password(), encryptionService, info.nickname()));

    createInitialSettingHistory(saved);

    return saved;
  }

  public void updateUserSettings(UpdateUserSettingInfo info) {
    Long userId = info.userId();
    User user = findById(userId);

    LocalTime newCheckInTime = info.newCheckInTime();
    int newCoreTime = info.newCoreTime();
    user.updateSettings(newCheckInTime, newCoreTime);

    LocalDate today = dateTimeProvider.getCurrentDate();
    deactivateCurrentHistory(userId, today);
    createNewSettingHistory(
        userId,
        today,
        newCheckInTime,
        newCoreTime,
        user.getCurrentSettings().getParticipationStatus());

    repository.save(user);

    log.info(
        "사용자 설정 변경: userId={}, checkInTime={}, coreTime={}", userId, newCheckInTime, newCoreTime);
  }

  private User findById(Long userId) {
    return repository.findById(userId);
  }

  private void createInitialSettingHistory(User user) {
    UserSettings settings = user.getCurrentSettings();
    LocalDate today = dateTimeProvider.getCurrentDate();

    UserSettingHistory initialHistory =
        UserSettingHistory.create(
            user.getId(),
            today,
            settings.getDesiredCheckInTime(),
            settings.getDesiredCoreTime(),
            settings.getParticipationStatus());

    settingHistoryRepository.save(initialHistory);

    log.info("초기 설정 이력 생성: userId={}", user.getId());
  }

  private void deactivateCurrentHistory(Long userId, LocalDate effectiveDate) {
    UserSettingHistory history = settingHistoryRepository.findCurrentActiveSettings(userId);

    history.deactivate(effectiveDate);
    settingHistoryRepository.save(history);

    log.info(
        "기존 설정 이력 비활성화: userId={}, historyId={}, effectiveToDate={}",
        userId,
        history.getId(),
        history.getEffectiveToDate());
  }

  private void createNewSettingHistory(
      Long userId,
      LocalDate effectiveDate,
      LocalTime checkInTime,
      int coreTime,
      ParticipationStatus participationStatus) {
    UserSettingHistory newHistory =
        UserSettingHistory.create(
            userId, effectiveDate, checkInTime, coreTime, participationStatus);

    settingHistoryRepository.save(newHistory);

    log.info(
        "새 설정 이력 생성: userId={}, effectiveFromDate={}, checkInTime={}, coreTime={}",
        userId,
        effectiveDate,
        checkInTime,
        coreTime);
  }

  private void isSamePasswordAndPasswordConfirm(SignupUserInfo info) {
    if (!info.password().equals(info.confirmPassword())) {
      throw UserValidationException.noPasswordConfirm();
    }
  }

  private void isEmailAlreadyRegistered(SignupUserInfo info) {
    String email = info.email();
    if (repository.existsByEmail(email)) {
      throw UserValidationException.duplicateEmail(email);
    }
  }
}
