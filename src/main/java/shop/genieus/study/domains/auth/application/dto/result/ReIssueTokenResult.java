package shop.genieus.study.domains.auth.application.dto.result;

import shop.genieus.study.domains.auth.domain.vo.TokenPair;

public record ReIssueTokenResult(TokenPair tokenPair, Long userId) {}
