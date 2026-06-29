package capstone.domain.v2.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//seller와 user의 role을 통합
@Getter
@RequiredArgsConstructor
public enum MemberV2Role {
    ROLE_USER("유저"),
    ROLE_SELLER("판매자"),
    ROLE_ADMIN("관리자");

    private final String description;
}
