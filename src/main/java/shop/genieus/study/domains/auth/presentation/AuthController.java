package shop.genieus.study.domains.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.response.AuthInfoResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  @GetMapping("/info")
  public ResponseEntity<AuthInfoResponse> getUserInfo(@AuthPrincipal CustomPrincipal principal) {
    return ResponseEntity.ok(AuthInfoResponse.from(principal));
  }
}
