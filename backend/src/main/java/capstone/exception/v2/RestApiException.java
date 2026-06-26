package capstone.exception.v2;

import capstone.exception.v2.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{ // RuntimeException(언체크 예외)을 상속

    private final ErrorCode errorCode; //Enum(ErrorCode)을 캡슐화

    public synchronized Throwable fillInStackTrace(){
        return this; // 스택 트레이스를 덮어쓰지 않고 자기 자신을 그대로 반환 (생성 비용 없음)
    }
}
