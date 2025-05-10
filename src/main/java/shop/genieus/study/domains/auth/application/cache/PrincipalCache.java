package shop.genieus.study.domains.auth.application.cache;

import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

public interface PrincipalCache {
  CustomPrincipal findById(Long userId);

  CustomPrincipal save(Long userId, CustomPrincipal principal);
}
