package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import static shop.genieus.study.domains.stamp.domain.entity.QStampView.stampView;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.stamp.application.dto.info.get.GetStampSearchInfo;
import shop.genieus.study.domains.stamp.domain.entity.StampView;
import shop.genieus.study.domains.stamp.domain.vo.StampType;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StampViewSearchRepository {
  private final JPAQueryFactory queryFactory;

  private final Map<String, Expression<?>> sortFieldMap =
      Map.of(
          "verifiedat", stampView.verifiedAt,
          "title", stampView.title,
          "nickname", stampView.nickname,
          "type", stampView.type);

  public Page<StampView> findStampViews(
      GetStampSearchInfo.StampFilterInfo filterParams, Pageable pageable) {
    List<BooleanExpression> conditions = buildConditions(filterParams);
    BooleanExpression whereCondition =
        conditions.stream().reduce(BooleanExpression::and).orElse(null);

    List<StampView> content =
        queryFactory
            .selectFrom(stampView)
            .where(whereCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderSpecifier(filterParams))
            .fetch();

    JPAQuery<Long> countQuery =
        queryFactory.select(stampView.count()).from(stampView).where(whereCondition);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<BooleanExpression> buildConditions(GetStampSearchInfo.StampFilterInfo filterParams) {
    List<BooleanExpression> conditions = new ArrayList<>();

    // 타입 필터
    if (filterParams.type() != null) {
      conditions.add(typeEq(filterParams.type()));
    }

    // 제목 검색
    if (filterParams.title() != null && !filterParams.title().trim().isEmpty()) {
      conditions.add(titleContains(filterParams.title().trim()));
    }

    // 카테고리 필터
    if (filterParams.category() != null && !filterParams.category().trim().isEmpty()) {
      conditions.add(categoryContains(filterParams.category().trim()));
    }

    // 날짜 범위 필터
    BooleanExpression dateCondition =
        createdAtBetween(filterParams.startDate(), filterParams.endDate());
    if (dateCondition != null) {
      conditions.add(dateCondition);
    }

    return conditions;
  }

  private BooleanExpression typeEq(StampType type) {
    return stampView.type.eq(type);
  }

  private BooleanExpression titleContains(String title) {
    return stampView.title.containsIgnoreCase(title);
  }

  private BooleanExpression categoryContains(String category) {
    BooleanExpression condition = null;

    BooleanExpression categoryCondition =
        stampView
            .careerType
            .containsIgnoreCase(category)
            .or(stampView.activityType.containsIgnoreCase(category))
            .or(stampView.algorithmType.containsIgnoreCase(category))
            .or(stampView.platformType.containsIgnoreCase(category))
            .or(stampView.categoryType.containsIgnoreCase(category));

    condition = condition == null ? categoryCondition : condition.or(categoryCondition);

    return condition;
  }

  private BooleanExpression createdAtBetween(LocalDate startDate, LocalDate endDate) {
    try {
      if (startDate != null && endDate != null) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59, 999999999);
        return stampView.verifiedAt.between(startDateTime, endDateTime);
      } else if (startDate != null) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        return stampView.verifiedAt.goe(startDateTime);
      } else if (endDate != null) {
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59, 999999999);
        return stampView.verifiedAt.loe(endDateTime);
      }
    } catch (Exception e) {
      log.warn("날짜 필터링 형성 실패: {}", e.getMessage());
    }
    return null;
  }

  private OrderSpecifier[] getOrderSpecifier(GetStampSearchInfo.StampFilterInfo filterParams) {
    String sortBy = filterParams.sortBy().toLowerCase();
    String direction = filterParams.sortDirection().toUpperCase();

    Order order = "ASC".equals(direction) ? Order.ASC : Order.DESC;
    Expression<?> sortField = sortFieldMap.getOrDefault(sortBy, stampView.verifiedAt);

    return new OrderSpecifier[] {new OrderSpecifier(order, sortField)};
  }
}
