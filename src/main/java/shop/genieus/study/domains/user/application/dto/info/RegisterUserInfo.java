package shop.genieus.study.domains.user.application.dto.info;

public record RegisterUserInfo(
    String email, String password, String confirmPassword, String nickname) {}
