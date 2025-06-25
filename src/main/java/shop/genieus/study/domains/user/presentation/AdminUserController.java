package shop.genieus.study.domains.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.user.application.UserCommandService;
import shop.genieus.study.domains.user.application.UserQueryService;
import shop.genieus.study.domains.user.application.dto.result.GetAdminUserSearchResult;
import shop.genieus.study.domains.user.presentation.dto.UserInfoMapper;
import shop.genieus.study.domains.user.presentation.dto.request.AdminUserFilterParams;
import shop.genieus.study.domains.user.presentation.dto.request.ApproveUserRequest;
import shop.genieus.study.domains.user.presentation.dto.request.UpdateParticipationStatusRequest;
import shop.genieus.study.domains.user.presentation.dto.response.AdminUserListResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
  private final UserCommandService userCommandService;
  private final UserQueryService userQueryService;
  private final UserInfoMapper userInfoMapper;

  @PostMapping("/{userId}/approve")
  public ResponseEntity<Void> approveUser(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestBody @Valid ApproveUserRequest request) {

    userCommandService.approveUser(request.toInfo(principal.id(), userId));

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{userId}/participation-status")
  public ResponseEntity<Void> updateParticipationStatus(
      @AuthPrincipal CustomPrincipal principal,
      @PathVariable Long userId,
      @RequestBody @Valid UpdateParticipationStatusRequest request) {

    userCommandService.updateParticipationStatus(request.toInfo(principal.id(), userId));

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<AdminUserListResponse> getUserList(
      @ModelAttribute AdminUserFilterParams filterParams,
      @PageableDefault(page = 0, size = 10) Pageable pageable) {

    GetAdminUserSearchResult result =
        userQueryService.getAdminUserList(userInfoMapper.toInfo(filterParams, pageable));

    return ResponseEntity.ok(AdminUserListResponse.from(result));
  }
}
