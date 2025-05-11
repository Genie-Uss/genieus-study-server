package shop.genieus.study.domains.stamp.application.dto.info.create;

import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;

public record CreateResumeStampInfo(
    Long userId,
    String companyName,
    CareerType careerType,
    ActivityType activityType,
    String description,
    String relatedUrl) {}
