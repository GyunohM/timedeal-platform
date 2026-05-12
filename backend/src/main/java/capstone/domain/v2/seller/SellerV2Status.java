package capstone.domain.v2.seller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellerStatus {

    ACTIVE("활성"),
    DELETED("탈퇴"),
    LOCKED("정지"),
    DORMANT("휴먼");
    
    private final String discription;
}
