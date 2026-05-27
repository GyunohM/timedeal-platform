package capstone.domain.v2.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
//RequiredArgsConstructor 알아보기
@Getter
@RequiredArgsConstructor
public enum UserV2Status {
    ACTIVE("활성"),
    DELETED("탈퇴"),
    LOCKED("정지"),
    DORMANT("휴먼");

    private final String description;
}
