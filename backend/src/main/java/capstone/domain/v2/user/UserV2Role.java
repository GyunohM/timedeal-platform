package capstone.domain.v2.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserV2Role {

    ROLE_USER("유저"),
    ROLE_ADMIN("관리자");

    private final String description;
}
