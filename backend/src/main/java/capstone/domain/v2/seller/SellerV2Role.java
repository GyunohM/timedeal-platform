package capstone.domain.v2.seller;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellerV2Role {
    ROLE_USER("유저"),
    ROLE_ADMIN("관리자");

    private final String description;
}
