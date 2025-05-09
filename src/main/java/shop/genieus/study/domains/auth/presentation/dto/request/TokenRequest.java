package shop.genieus.study.domains.auth.presentation.dto.request;

public record TokenRequest(String accessToken, String refreshToken) {}
