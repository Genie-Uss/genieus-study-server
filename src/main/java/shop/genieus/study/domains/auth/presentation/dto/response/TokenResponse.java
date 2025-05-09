package shop.genieus.study.domains.auth.presentation.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
    String accessToken, String refreshToken, Long id, String role, String nickname) {}
