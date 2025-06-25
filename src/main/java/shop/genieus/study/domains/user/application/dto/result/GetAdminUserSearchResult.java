package shop.genieus.study.domains.user.application.dto.result;

import java.util.List;
import shop.genieus.study.domains.user.application.dto.info.AdminUserInfo;

public record GetAdminUserSearchResult(List<AdminUserInfo> content, PageInfo page) {}
