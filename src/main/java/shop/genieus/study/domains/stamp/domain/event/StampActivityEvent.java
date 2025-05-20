package shop.genieus.study.domains.stamp.domain.event;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;

@Getter
@RequiredArgsConstructor
public class StampActivityEvent implements NotificationMessageBuilder {
  private static final int MAX_DESCRIPTION_LENGTH = 150;

  private final Long userId;
  private final String title;
  private final String category;
  private final String description;
  private final String url;
  private final StampTypeWrapper stampType;

  public static StampActivityEvent of(Stamp stamp) {
    return switch (stamp.getType()) {
      case CT -> {
        CodingTestStamp s = (CodingTestStamp) stamp;
        yield new StampActivityEvent(
            s.getUserId(),
            null,
            inlineCodes(s.getAlgorithmType().getFieldName(), s.getPlatformType().getFieldName()),
            truncateText(s.getDescription()),
            safeUrl(s.getProblemUrl()),
            StampTypeWrapper.CT);
      }
      case TIL -> {
        TilStamp s = (TilStamp) stamp;
        yield new StampActivityEvent(
            s.getUserId(),
            s.getTitle(),
            inlineCode(s.getCategoryType().getFieldName()),
            truncateText(s.getContent()),
            safeUrl(s.getRelatedUrl()),
            StampTypeWrapper.TIL);
      }
      case RESUME -> {
        ResumeStamp s = (ResumeStamp) stamp;
        yield new StampActivityEvent(
            s.getUserId(),
            s.getTitle(),
            inlineCodes(s.getCareerType().getFieldName(), s.getActivityType().getFieldName()),
            truncateText(s.getDescription()),
            safeUrl(s.getRelatedUrl()),
            StampTypeWrapper.RESUME);
      }
    };
  }

  private static String safeUrl(String url) {
    return (url == null || url.isBlank()) ? null : url;
  }

  private static String inlineCode(String text) {
    return "`" + text + "`";
  }

  private static String inlineCodes(String... texts) {
    return Arrays.stream(texts)
        .map(StampActivityEvent::inlineCode)
        .collect(Collectors.joining(" "));
  }

  private static String truncateText(String text) {
    if (text == null || text.length() <= MAX_DESCRIPTION_LENGTH) {
      return text;
    }
    return text.substring(0, MAX_DESCRIPTION_LENGTH - 3) + "...";
  }

  @Override
  public String buildTitle() {
    return "**[_" + stampType.getDisplayName() + "_] 도장 알림**";
  }

  @Override
  public String buildMessage() {
    StringBuilder sb = new StringBuilder();

    sb.append("_**%s**_님의 인증\n\n");

    if (title != null && !title.isBlank()) {
      sb.append("**").append(title).append("**\n");
    }

    sb.append(description).append("\n\n").append("_**#**_ ").append(category);

    if (url != null) {
      sb.append("　　:link: [링크](").append(url).append(")");
    }

    return sb.toString();
  }

  @Override
  public String getColorCode() {
    return "10181046";
  }

  @Override
  public NotificationChannelType getChannelType() {
    return NotificationChannelType.ACTIVITY;
  }

  @Override
  public String getEmoji() {
    return "🟣 ";
  }

  @Getter(AccessLevel.PRIVATE)
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public enum StampTypeWrapper {
    CT("코딩테스트"),
    TIL("TIL"),
    RESUME("자소서");

    private final String displayName;
  }
}
