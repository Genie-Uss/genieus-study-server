package shop.genieus.study.domains.user.application;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.user.application.dto.info.SignupUserInfo;
import shop.genieus.study.domains.user.application.dto.info.UpdateParticipationStatusInfo;
import shop.genieus.study.domains.user.application.dto.info.UpdateUserSettingInfo;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;
import shop.genieus.study.domains.user.domain.vo.UserSettings;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserCommandService {
  private final UserRepository repository;
  private final UserSettingHistoryCommandService historyCommandService;
  private final PasswordEncryptionService encryptionService;
  private final DateTimeProvider dateTimeProvider;

  public User signupUser(SignupUserInfo info) {
    isSamePasswordAndPasswordConfirm(info);
    isEmailAlreadyRegistered(info);

    User saved =
        repository.save(
            User.create(info.email(), info.password(), encryptionService, info.nickname()));

    LocalDate today = dateTimeProvider.getCurrentDate();
    historyCommandService.createInitialSettingHistory(saved, today);

    return saved;
  }

  public void updateUserSettings(UpdateUserSettingInfo info) {
    Long userId = info.userId();
    User user = findById(userId);

    LocalTime newCheckInTime = info.newCheckInTime();
    int newCoreTime = info.newCoreTime();
    ParticipationStatus currentStatus = user.getCurrentSettings().getParticipationStatus();

    user.updateSettings(newCheckInTime, newCoreTime, currentStatus);

    LocalDate today = dateTimeProvider.getCurrentDate();
    historyCommandService.updateSettingHistory(
        userId, today, newCheckInTime, newCoreTime, currentStatus);

    repository.save(user);

    log.info(
        "사용자 설정 변경: userId={}, checkInTime={}, coreTime={}", userId, newCheckInTime, newCoreTime);
  }

  public void updateParticipationStatus(UpdateParticipationStatusInfo info) {
    Long adminUserId = info.adminUserId();
    Long targetUserId = info.targetUserId();
    ParticipationStatus newStatus = info.participationStatus();
    String reason = info.reason();

    User targetUser = findById(targetUserId);
    UserSettings currentSettings = targetUser.getCurrentSettings();
    ParticipationStatus currentStatus = currentSettings.getParticipationStatus();

    if (currentStatus == newStatus) {
      throw UserValidationException.sameParticipationStatus();
    }

    targetUser.updateSettings(
        currentSettings.getDesiredCheckInTime(), currentSettings.getDesiredCoreTime(), newStatus);

    LocalDate today = dateTimeProvider.getCurrentDate();
    historyCommandService.updateSettingHistory(
        targetUserId,
        today,
        currentSettings.getDesiredCheckInTime(),
        currentSettings.getDesiredCoreTime(),
        newStatus);

    repository.save(targetUser);

    log.info(
        "참여 상태 변경: adminUserId={}, targetUserId={}, {} -> {}, reason='{}'",
        adminUserId,
        targetUserId,
        currentStatus,
        newStatus,
        reason);
  }

  private User findById(Long userId) {
    return repository.findById(userId);
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
