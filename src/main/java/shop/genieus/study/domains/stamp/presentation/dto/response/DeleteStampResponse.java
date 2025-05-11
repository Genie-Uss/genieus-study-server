package shop.genieus.study.domains.stamp.presentation.dto.response;

public record DeleteStampResponse(boolean success) {
  public static DeleteStampResponse mock() {
    return new DeleteStampResponse(true);
  }
}
