package shop.genieus.study.domains.auth.presentation.dto.response;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(boolean success, String accessToken, String refreshToken) {}
