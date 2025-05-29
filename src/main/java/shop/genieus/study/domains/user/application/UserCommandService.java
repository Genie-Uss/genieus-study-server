package shop.genieus.study.domains.user.application;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.user.application.dto.info.ApproveUserInfo;
import shop.genieus.study.domains.user.application.dto.info.SignupUserInfo;
import shop.genieus.study.domains.user.application.dto.info.UpdateParticipationStatusInfo;
import shop.genieus.study.domains.user.application.dto.info.UpdateUserSettingInfo;
import shop.genieus.study.domains.user.application.repository.UserCacheRepository;
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
  private final UserCacheRepository userCacheRepository;
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

    log.info("새 사용자 가입: userId={}, email={}, status=PENDING", saved.getId(), info.email());

    return saved;
  }

  public void approveUser(ApproveUserInfo info) {
    Long adminUserId = info.adminUserId();
    Long targetUserId = info.targetUserId();

    User targetUser = findById(targetUserId);

    if (!targetUser.isPending()) {
      throw UserValidationException.userNotPending();
    }

    targetUser.approveWithParticipation(info.isParticipating());
    User updated = repository.save(targetUser);

    LocalDate today = dateTimeProvider.getCurrentDate();
    historyCommandService.updateSettingHistory(updated, today);

    if (updated.isParticipating()) {
      userCacheRepository.invalidateParticipatingUsers();
    }

    log.info(
        "사용자 승인 완료: adminUserId={}, targetUserId={}, participationStatus={}",
        adminUserId,
        targetUserId,
        targetUser.getCurrentSettings().getParticipationStatus());
  }

  public void updateUserSettings(UpdateUserSettingInfo info) {
    Long userId = info.userId();
    User user = findById(userId);

    LocalTime newCheckInTime = info.newCheckInTime();
    int newCoreTime = info.newCoreTime();
    ParticipationStatus currentStatus = user.getCurrentSettings().getParticipationStatus();

    user.updateSettings(newCheckInTime, newCoreTime, currentStatus);

    User updated = repository.save(user);
    LocalDate today = dateTimeProvider.getCurrentDate();
    historyCommandService.updateSettingHistory(updated, today);

    log.info(
        "사용자 설정 변경: userId={}, checkInTime={}, coreTime={}", userId, newCheckInTime, newCoreTime);
  }

  public void updateParticipationStatus(UpdateParticipationStatusInfo info) {
    Long adminUserId = info.adminUserId();
    Long targetUserId = info.targetUserId();
    ParticipationStatus newStatus = info.participationStatus();

    User targetUser = findById(targetUserId);
    UserSettings currentSettings = targetUser.getCurrentSettings();
    ParticipationStatus currentStatus = currentSettings.getParticipationStatus();

    if (currentStatus == newStatus) {
      throw UserValidationException.sameParticipationStatus();
    }

    targetUser.updateSettings(
        currentSettings.getDesiredCheckInTime(), currentSettings.getDesiredCoreTime(), newStatus);

    User updated = repository.save(targetUser);
    LocalDate today = dateTimeProvider.getCurrentDate();
    historyCommandService.updateSettingHistory(updated, today);

    userCacheRepository.invalidateParticipatingUsers();

    log.info(
        "참여 상태 변경: adminUserId={}, targetUserId={}, {} -> {}",
        adminUserId,
        targetUserId,
        currentStatus,
        newStatus);
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
