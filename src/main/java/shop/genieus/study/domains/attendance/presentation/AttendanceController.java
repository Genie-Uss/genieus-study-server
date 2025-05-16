package shop.genieus.study.domains.attendance.presentation;

import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.genieus.study.domains.attendance.application.AttendanceService;
import shop.genieus.study.domains.attendance.application.dto.info.CheckInInfo;
import shop.genieus.study.domains.attendance.application.dto.info.CheckOutInfo;
import shop.genieus.study.domains.attendance.application.dto.info.GetAttendanceInfo;
import shop.genieus.study.domains.attendance.presentation.dto.request.CheckInRequest;
import shop.genieus.study.domains.attendance.presentation.dto.request.CheckOutRequest;
import shop.genieus.study.domains.attendance.presentation.dto.response.AttendanceResponse;
import shop.genieus.study.domains.attendance.presentation.dto.response.CheckInResponse;
import shop.genieus.study.domains.attendance.presentation.dto.response.CheckOutResponse;
import shop.genieus.study.domains.auth.presentation.annotation.AuthPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendances")
public class AttendanceController {
  private final AttendanceService attendanceService;

  @PostMapping("/check-in")
  public ResponseEntity<CheckInResponse> checkIn(
      @AuthPrincipal CustomPrincipal principal, @RequestBody @Valid CheckInRequest request) {
    CheckInResponse response =
        CheckInResponse.from(
            attendanceService.checkIn(new CheckInInfo(principal.id(), request.checkInDateTime())));

    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/check-out")
  public ResponseEntity<CheckOutResponse> checkOut(
      @AuthPrincipal CustomPrincipal principal, @RequestBody @Valid CheckOutRequest request) {
    CheckOutResponse response =
        CheckOutResponse.from(
            attendanceService.checkOut(
                new CheckOutInfo(principal.id(), request.checkOutDateTime())));

    return ResponseEntity.ok().body(response);
  }

  @GetMapping
  public ResponseEntity<AttendanceResponse> getAttendance(
      @AuthPrincipal CustomPrincipal principal,
      @RequestParam(required = false) LocalDate date,
      @RequestParam Long userId) {
    LocalDate targetDate = (date != null) ? date : LocalDate.now();
    AttendanceResponse response =
        AttendanceResponse.from(
            attendanceService.getAttendance(new GetAttendanceInfo(userId, targetDate)),
            principal.id(),
            userId);

    return ResponseEntity.ok().body(response);
  }
}
