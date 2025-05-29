package shop.genieus.study.domains.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.user.application.UserCommandService;
import shop.genieus.study.domains.user.presentation.dto.request.UpdateParticipationStatusRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
  private final UserCommandService userCommandService;

  @PatchMapping("/{userId}/participation-status")
  public ResponseEntity<Void> updateParticipationStatus(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestBody @Valid UpdateParticipationStatusRequest request) {

    userCommandService.updateParticipationStatus(request.toInfo(principal.id(), userId));

    return ResponseEntity.noContent().build();
  }
}
