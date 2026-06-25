package capstone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{ // RuntimeException(언체크 예외)을 상속

    private final ErrorCode errorCode; //Enum(ErrorCode)을 캡슐화
}
