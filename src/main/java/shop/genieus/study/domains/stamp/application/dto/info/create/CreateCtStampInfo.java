package shop.genieus.study.domains.stamp.application.dto.info.create;

import shop.genieus.study.domains.stamp.domain.vo.AlgorithmType;
import shop.genieus.study.domains.stamp.domain.vo.PlatformType;

public record CreateCtStampInfo(
    Long userId,
    AlgorithmType algorithmType,
    PlatformType platformType,
    String description,
    String problemUrl) {}
