package shop.genieus.study.domains.auth.presentation.dto.response;

public record AuthInfoResponse(Long id, String nickname, String role, String profileImage) {
  public static AuthInfoResponse mock() {
    return new AuthInfoResponse(
        1L,
        "홍길동",
        "ROLE_USER",
        "https://content.surfit.io/thumbs/image/3MDXK/79mkd/2074604343681b3d8f9775d/cover-top-2x.webp");
  }
}
