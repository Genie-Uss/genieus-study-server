package shop.genieus.study.domains.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.genieus.study.domains.user.application.UserService;
import shop.genieus.study.domains.user.presentation.dto.request.RegisterUserRequest;
import shop.genieus.study.domains.user.presentation.dto.response.RegisterUserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  @PostMapping
  public ResponseEntity<RegisterUserResponse> signup(@RequestBody RegisterUserRequest request) {
    RegisterUserResponse response = RegisterUserResponse.from(service.registerUser(request.to()));
    return ResponseEntity.ok().body(response);
  }
}
