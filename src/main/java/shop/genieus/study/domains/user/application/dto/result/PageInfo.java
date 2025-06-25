package shop.genieus.study.domains.user.application.dto.result;

public record PageInfo(int page, int size, long totalElements, int totalPages, boolean last) {}
