package shop.genieus.study.domains.stamp.domain.entity;

import shop.genieus.study.domains.stamp.domain.vo.*;

public interface DescribableStamp {
  default String[] getCategories() {
    return new String[0];
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

  default AlgorithmType getAlgorithmType() {
    return null;
  }

  default PlatformType getPlatformType() {
    return null;
  }

  default CategoryType getCategoryType() {
    return null;
  }

  default ActivityType getActivityType() {
    return null;
  }

  default CareerType getCareerType() {
    return null;
  }
}
