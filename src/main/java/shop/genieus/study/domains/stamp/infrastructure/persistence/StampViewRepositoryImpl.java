package shop.genieus.study.domains.stamp.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.stamp.application.repository.StampViewRepository;
import shop.genieus.study.domains.stamp.domain.entity.StampView;
import shop.genieus.study.domains.stamp.infrastructure.persistence.repository.StampViewJpaRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StampViewRepositoryImpl implements StampViewRepository {
  @PersistenceContext private final EntityManager em;
  private final StampViewJpaRepository jpaRepository;

  public StampView save(StampView view) {
    em.createNativeQuery(
            """
                      INSERT INTO g_stamp_views (
                          id, activity_type, algorithm_type, career_type,
                          category_type, nickname, platform_type, title, type, user_id, verified_at
                      )
                      VALUES (
                          :id, :activityType, :algorithmType, :careerType,
                          :categoryType, :nickname, :platformType, :title, :type, :userId, :verifiedAt
                      )
                      """)
        .setParameter("id", view.getId())
        .setParameter("activityType", view.getActivityType())
        .setParameter("algorithmType", view.getAlgorithmType())
        .setParameter("careerType", view.getCareerType())
        .setParameter("categoryType", view.getCategoryType())
        .setParameter("nickname", view.getNickname())
        .setParameter("platformType", view.getPlatformType())
        .setParameter("title", view.getTitle())
        .setParameter("type", view.getType().name())
        .setParameter("userId", view.getUserId())
        .setParameter("verifiedAt", view.getVerifiedAt())
        .executeUpdate();

    return view;
  }
}
