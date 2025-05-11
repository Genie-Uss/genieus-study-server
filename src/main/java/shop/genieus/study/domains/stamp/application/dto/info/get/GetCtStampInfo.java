package shop.genieus.study.domains.stamp.application.dto.info.get;


import java.time.LocalDate;

public record GetCtStampInfo(
    Long userId,
    LocalDate date
){ }
