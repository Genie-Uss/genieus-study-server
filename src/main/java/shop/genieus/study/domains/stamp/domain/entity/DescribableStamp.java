package shop.genieus.study.domains.stamp.domain.entity;

public interface DescribableStamp {
  default String[] getCategories() {
    return null;
  }

  default String getTitle() {
    return null;
  }

  default String getContent() {
    return null;
  }

  default String getUrl() {
    return null;
  }
}
