package shop.genieus.study.domains.stamp.presentation.dto.response.read;

import java.util.List;
import java.util.Map;

public record StampCategoryResponse(List<String> field, Map<String,List<EnumResponse>> categories) {}
