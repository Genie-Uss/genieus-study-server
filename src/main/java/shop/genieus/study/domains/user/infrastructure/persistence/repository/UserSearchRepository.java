package shop.genieus.study.domains.user.infrastructure.persistence.repository;

import static shop.genieus.study.domains.user.domain.entity.QUser.user;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.user.application.dto.info.AdminUserInfo;
import shop.genieus.study.domains.user.application.dto.info.GetAdminUserSearchInfo;
import shop.genieus.study.domains.user.application.dto.info.QAdminUserInfo;
import shop.genieus.study.domains.user.domain.vo.AccountStatus;
import shop.genieus.study.domains.user.domain.vo.ParticipationStatus;

@Repository
@RequiredArgsConstructor
public class UserSearchRepository {
  private final JPAQueryFactory queryFactory;

  private final Map<String, Expression<?>> sortFieldMap =
      Map.of(
          "createdat", user.createdAt,
          "updatedat", user.updatedAt,
          "nickname", user.nickname.value,
          "email", user.email.value,
          "status", user.status);

  public Page<AdminUserInfo> findUsers(
      GetAdminUserSearchInfo.AdminUserFilterInfo filterInfo, Pageable pageable) {
    List<BooleanExpression> conditions = buildConditions(filterInfo);
    BooleanExpression whereCondition =
        conditions.stream().reduce(BooleanExpression::and).orElse(null);

    List<AdminUserInfo> content =
        queryFactory
            .select(
                new QAdminUserInfo(
                    user.id,
                    user.nickname.value,
                    user.email.value,
                    user.status.stringValue(),
                    user.currentSettings.participationStatus,
                    user.role.stringValue(),
                    user.createdAt,
                    user.updatedAt))
            .from(user)
            .where(whereCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderSpecifier(filterInfo))
            .fetch();

    JPAQuery<Long> countQuery = queryFactory.select(user.count()).from(user).where(whereCondition);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<BooleanExpression> buildConditions(
      GetAdminUserSearchInfo.AdminUserFilterInfo filterInfo) {
    List<BooleanExpression> conditions = new ArrayList<>();

    // 상태 필터
    if (filterInfo.status() != null && !filterInfo.status().isBlank()) {
      conditions.add(statusEq(filterInfo.status()));
    }

    // 참여 상태 필터
    if (filterInfo.participationStatus() != null && !filterInfo.participationStatus().isBlank()) {
      conditions.add(participationStatusEq(filterInfo.participationStatus()));
    }

    // 닉네임 검색
    if (filterInfo.nickname() != null && !filterInfo.nickname().isBlank()) {
      conditions.add(nicknameContains(filterInfo.nickname()));
    }

    // 이메일 검색
    if (filterInfo.email() != null && !filterInfo.email().isBlank()) {
      conditions.add(emailContains(filterInfo.email()));
    }

    return conditions;
  }

  private BooleanExpression statusEq(String status) {
    try {
      AccountStatus userStatus = AccountStatus.valueOf(status.toUpperCase());
      return user.status.eq(userStatus);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private BooleanExpression participationStatusEq(String participationStatus) {
    ParticipationStatus userStatus = ParticipationStatus.valueOf(participationStatus.toUpperCase());
    return user.currentSettings.participationStatus.eq(userStatus);
  }

  private BooleanExpression nicknameContains(String nickname) {
    return user.nickname.value.containsIgnoreCase(nickname);
  }

  private BooleanExpression emailContains(String email) {
    return user.email.value.containsIgnoreCase(email);
  }

  private OrderSpecifier<?>[] getOrderSpecifier(
      GetAdminUserSearchInfo.AdminUserFilterInfo filterInfo) {
    String sortBy = filterInfo.sortBy() != null ? filterInfo.sortBy() : "createdAt";
    String sortDirection = filterInfo.sortDirection() != null ? filterInfo.sortDirection() : "desc";

    Expression<?> sortExpression = sortFieldMap.get(sortBy.toLowerCase());
    if (sortExpression == null) {
      sortExpression = user.createdAt;
    }

    Order order = "asc".equalsIgnoreCase(sortDirection) ? Order.ASC : Order.DESC;

    return new OrderSpecifier[] {new OrderSpecifier(order, sortExpression)};
  }
}
