package shop.genieus.study.domains.stamp.application.dto.result;

import java.time.LocalDateTime;
import shop.genieus.study.domains.stamp.domain.entity.JobActivityStamp;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

public record CreateJobActivityStampResult(
    Long id,
    StampType type,
    boolean isVerified,
    LocalDateTime verifiedAt,
    String companyName,
    CareerType careerType,
    ActivityType activityType,
    String relatedUrl
) {
    public static CreateJobActivityStampResult of(JobActivityStamp stamp){
        return new CreateJobActivityStampResult(
            stamp.getId(),
            stamp.getType(),
            stamp.getIsVerified(),
            stamp.getVerifiedAt(),
            stamp.getCompanyName(),
            stamp.getCareerType(),
            stamp.getActivityType(),
            stamp.getRelatedUrl()
        );
    }
}
