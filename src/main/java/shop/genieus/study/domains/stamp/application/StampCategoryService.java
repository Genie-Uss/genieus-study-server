package shop.genieus.study.domains.stamp.application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.CategoryType;
import shop.genieus.study.domains.stamp.domain.vo.CareerType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;
import shop.genieus.study.domains.stamp.domain.vo.ActivityType;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.EnumResponse;
import shop.genieus.study.domains.stamp.presentation.dto.response.read.StampCategoryResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampCategoryService {

    public StampCategoryResponse getCategories(String type) {
        return switch (type.toLowerCase()) {
            case "ct" -> buildCtResponse();
            case "til" -> buildTilResponse();
            case "resume" -> buildResumeResponse();
            default -> throw new IllegalArgumentException("잘못된 타입입니다: " + type);
        };
    }

    private StampCategoryResponse buildCtResponse() {
        Map<String, List<EnumResponse>> map = new HashMap<>();

        map.put("algorithmTypes", Arrays.stream(AlgorithmType.values())
            .map(e -> new EnumResponse(e.name(), e.getFieldName()))
            .collect(Collectors.toList()));

        map.put("platformTypes", Arrays.stream(PlatformType.values())
            .map(e -> new EnumResponse(e.name(), e.getFieldName()))
            .collect(Collectors.toList()));

        return new StampCategoryResponse(List.of("algorithmTypes", "platformTypes"), map);
    }

    private StampCategoryResponse buildTilResponse() {
        Map<String, List<EnumResponse>> map = new HashMap<>();

        map.put("categoryTypes", Arrays.stream(CategoryType.values())
            .map(e -> new EnumResponse(e.name(), e.getFieldName()))
            .collect(Collectors.toList()));
        return new StampCategoryResponse(List.of("categoryTypes"), map);
    }

    private StampCategoryResponse buildResumeResponse() {
        Map<String, List<EnumResponse>> map = new HashMap<>();
        map.put("careerTypes", Arrays.stream(CareerType.values())
            .map(e -> new EnumResponse(e.name(), e.getFieldName()))
            .collect(Collectors.toList()));
        map.put("activityTypes", Arrays.stream(ActivityType.values())
            .map(e -> new EnumResponse(e.name(), e.getFieldName()))
            .collect(Collectors.toList()));
        return new StampCategoryResponse(List.of("careerTypes", "activityTypes"), map);
    }
}
