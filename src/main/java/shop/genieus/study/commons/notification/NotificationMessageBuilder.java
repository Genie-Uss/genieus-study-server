package shop.genieus.study.commons.notification;

public interface NotificationMessageBuilder {
  Long getUserId();

  String buildTitle();

  String buildMessage();

  String getColorCode();

  NotificationChannelType getChannelType();

  String getEmoji();
}
