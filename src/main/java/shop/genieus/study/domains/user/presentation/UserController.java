package shop.genieus.study.domains.user.presentation;

import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.user.application.UserCommandService;
import shop.genieus.study.domains.user.application.UserQueryService;
import shop.genieus.study.domains.user.presentation.dto.request.SignupUserRequest;
import shop.genieus.study.domains.user.presentation.dto.request.UserSettingUpdateRequest;
import shop.genieus.study.domains.user.presentation.dto.response.CheckAvailableResponse;
import shop.genieus.study.domains.user.presentation.dto.response.SignupUserResponse;
import shop.genieus.study.domains.user.presentation.dto.response.UserInfoResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserQueryService queryService;
  private final UserCommandService commandService;

  @PostMapping
  public ResponseEntity<SignupUserResponse> signup(@RequestBody @Valid SignupUserRequest request) {
    SignupUserResponse response = SignupUserResponse.from(commandService.signupUser(request.to()));
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/check-nickname")
  public ResponseEntity<CheckAvailableResponse> checkNickname(@RequestParam String nickname) {
    return ResponseEntity.ok()
        .body(new CheckAvailableResponse(queryService.checkNicknameAvailable(nickname)));
  }

  @GetMapping("/check-email")
  public ResponseEntity<CheckAvailableResponse> checkEmail(@RequestParam String email) {
    return ResponseEntity.ok()
        .body(new CheckAvailableResponse(queryService.checkEmailAvailable(email)));
  }

  @GetMapping("/{userId}/info")
  public ResponseEntity<UserInfoResponse> getUserInfo(
      @AuthPrincipal CustomPrincipal principal, @PathVariable Long userId) {
    boolean isOwner = Objects.equals(principal.id(), userId);
    UserInfoResponse response =
        isOwner
            ? UserInfoResponse.from(principal, isOwner)
            : UserInfoResponse.from(queryService.getUserInfo(userId), isOwner);
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/{userId}/settings")
  public ResponseEntity<Void> updateUserSettings(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestBody @Valid UserSettingUpdateRequest request) {
    commandService.updateUserSettings(request.create(principal.id(), userId));

    return ResponseEntity.noContent().build();
  }
}
