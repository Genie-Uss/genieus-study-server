package shop.genieus.study.domains.stamp.application.dto.result;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateResumeStampResult(
    Long id,
    StampType type,
    LocalDateTime verifiedAt,
    String companyName,
    CareerType careerType,
    ActivityType activityType,
    String relatedUrl
) {
    public static CreateResumeStampResult of(ResumeStamp stamp){
        return new CreateResumeStampResult(
            stamp.getId(),
            stamp.getType(),
            stamp.getVerifiedAt(),
            stamp.getCompanyName(),
            stamp.getCareerType(),
            stamp.getActivityType(),
            stamp.getRelatedUrl()
        );
    }
}
