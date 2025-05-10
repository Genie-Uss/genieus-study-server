package shop.genieus.study.domains.attendance.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.*;
import org.hibernate.annotations.Comment;
import shop.genieus.study.commons.jpa.BaseEntity;
import shop.genieus.study.domains.attendance.domain.exception.AttendanceValidationException;
import shop.genieus.study.domains.attendance.domain.vo.AttendanceTime;
import shop.genieus.study.domains.attendance.domain.vo.StudyResult;

@Entity
@Getter
@Comment("출석 테이블")
@Table(
    name = "g_attendances",
    indexes = {@Index(name = "idx_user_date", columnList = "userId, date")},
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_user_date",
          columnNames = {"userId", "date"})
    })
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity {
  @Id
  @Comment("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("사용자 외래키")
  private Long userId;

  @Comment("희망 코어 시간 (분)")
  private int desiredCoreTime;

  @Embedded private AttendanceTime attendanceTime;

  @Embedded private StudyResult studyResult;

  public static Attendance checkIn(
      Long userId,
      LocalDateTime checkInTime,
      LocalTime desiredCheckInTime,
      LocalDate currentDate,
      LocalDateTime currentDateTime,
      int desiredCoreTime) {
    AttendanceTime attendanceTime =
        AttendanceTime.of(checkInTime, null, desiredCheckInTime, currentDate, currentDateTime);
    StudyResult studyResult = StudyResult.initial();

    return Attendance.builder()
        .userId(userId)
        .desiredCoreTime(desiredCoreTime)
        .attendanceTime(attendanceTime)
        .studyResult(studyResult)
        .build();
  }

  public void checkOut(
      LocalDateTime checkOutTime, LocalDate currentDate, LocalDateTime currentDateTime) {
    if (this.attendanceTime.isCheckedOut()) {
      throw AttendanceValidationException.alreadyCheckedOut();
    }

    AttendanceTime updatedAttendanceTime =
        this.attendanceTime.updateCheckOutTime(checkOutTime, currentDate, currentDateTime);

    int actualStudyMinutes = updatedAttendanceTime.calculateStudyMinutes();
    StudyResult updatedStudyResult =
        StudyResult.calculate(actualStudyMinutes, this.desiredCoreTime);

    this.attendanceTime = updatedAttendanceTime;
    this.studyResult = updatedStudyResult;
  }

  public boolean isCheckedOut() {
    return this.attendanceTime.isCheckedOut();
  }
}
