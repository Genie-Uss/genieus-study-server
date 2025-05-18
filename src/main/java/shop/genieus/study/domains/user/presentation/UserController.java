package shop.genieus.study.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.user.application.UserService;
import shop.genieus.study.domains.user.presentation.dto.request.SignupUserRequest;
import shop.genieus.study.domains.user.presentation.dto.response.CheckAvailableResponse;
import shop.genieus.study.domains.user.presentation.dto.response.SignupUserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  @PostMapping
  public ResponseEntity<SignupUserResponse> signup(@RequestBody SignupUserRequest request) {
    SignupUserResponse response = SignupUserResponse.from(service.signupUser(request.to()));
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/check-nickname")
  public ResponseEntity<CheckAvailableResponse> checkNickname(@RequestParam String nickname) {
    return ResponseEntity.ok()
        .body(new CheckAvailableResponse(service.checkNicknameAvailable(nickname)));
  }

  @GetMapping("/check-email")
  public ResponseEntity<CheckAvailableResponse> checkEmail(@RequestParam String email) {
    return ResponseEntity.ok().body(new CheckAvailableResponse(service.checkEmailAvailable(email)));
  }
}
