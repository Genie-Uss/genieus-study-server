package shop.genieus.study.domains.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import shop.genieus.study.commons.jpa.BaseEntity;
import shop.genieus.study.domains.user.application.PasswordEncryptionService;
import shop.genieus.study.domains.user.domain.vo.Email;

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

  @Column(nullable = false)
  private Nickname nickname;

  private String profileImage;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  @Column(nullable = false)
  private Integer requiredVerifications;

  @Builder.Default
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
        .role(UserRole.ROLE_USER)
        .requiredVerifications(3)
        .build();
  }

  public boolean matchPassword(
      String plainPassword, PasswordEncryptionService passwordEncryptionService) {
    return this.password.matches(plainPassword, passwordEncryptionService);
  }
}
