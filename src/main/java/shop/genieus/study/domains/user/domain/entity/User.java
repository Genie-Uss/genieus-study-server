package shop.genieus.study.domains.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.*;
import org.hibernate.annotations.Comment;
import shop.genieus.study.commons.jpa.BaseEntity;
import shop.genieus.study.domains.user.application.PasswordEncryptionService;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;
import shop.genieus.study.domains.user.domain.vo.Password;
import shop.genieus.study.domains.user.domain.vo.UserRole;

@Entity
@Getter
@Comment("사용자 테이블")
@Table(name = "g_users")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
  @Id
  @Comment("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  @Comment("사용자 로그인 아이디(이메일)")
  private Email email;

  @Embedded
  @Comment("비밀번호 정보")
  @JsonIgnore
  private Password password;

  @Comment("닉네임")
  @Column(nullable = false, unique = true)
  private Nickname nickname;

  @Comment("프로필 이미지 url")
  private String profileImage;

  @Builder.Default
  @Comment("권한 정보")
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.ROLE_USER;

  @Builder.Default
  @Comment("희망 출석 시각")
  @Column(nullable = false)
  private LocalTime desiredCheckInTime = LocalTime.of(9, 0);

  @Builder.Default
  @Comment("희망 코어 시간 (분)")
  @Column(nullable = false)
  private int desiredCoreTime = 240;

  @Builder.Default
  @Comment("활성화 시간")
  @Column(nullable = false)
  private Boolean isActive = true;

  public static User create(
      String email,
      String password,
      PasswordEncryptionService passwordEncryptionService,
      String nickname) {
    return User.builder()
        .email(Email.of(email))
        .password(Password.of(password, passwordEncryptionService))
        .nickname(Nickname.of(nickname))
        .build();
  }

  public boolean matchPassword(
      String plainPassword, PasswordEncryptionService passwordEncryptionService) {
    return this.password.matches(plainPassword, passwordEncryptionService);
  }

  public boolean isRoleUser() {
    return this.role.isRoleUser();
  }

  public boolean isRoleAdmin() {
    return this.role.isRoleAdmin();
  }
}
