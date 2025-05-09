package shop.genieus.study.domains.auth.presentation.annotation;


public record CustomPrincipal(Long id, String role, String nickname) {}
