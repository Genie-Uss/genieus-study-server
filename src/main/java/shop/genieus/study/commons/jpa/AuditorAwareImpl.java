package shop.genieus.study.commons.jpa;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {
  @Override
  public Optional<Long> getCurrentAuditor() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null
          || !authentication.isAuthenticated()
          || authentication instanceof AnonymousAuthenticationToken) {
        return Optional.of(-1L);
      }

      Object principal = authentication.getPrincipal();
      if (principal instanceof CustomPrincipal) {
        CustomPrincipal customPrincipal = (CustomPrincipal) principal;
        return Optional.of(customPrincipal.id());
      }

      return Optional.of(-1L);
    } catch (Exception e) {
      return Optional.of(-1L);
    }
  }
}
