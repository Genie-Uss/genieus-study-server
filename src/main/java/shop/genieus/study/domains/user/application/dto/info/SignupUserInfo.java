package shop.genieus.study.domains.user.application.dto.info;

public record SignupUserInfo(
    String email, String password, String confirmPassword, String nickname) {}
