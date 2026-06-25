package capstone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter ////클래스 내부 필드의 Getter 메서드를 자동으로 생성
@RequiredArgsConstructor //  final이 붙은 필수 필드를 매개변수로 받는 생성자를 자동으로 만듦
public enum ErrorCode {

    //공통 에러 항목 - 추후 추가 예정
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "입력 형식이 잘못되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    //판매자 회원가입 도메인 전용 에러 항목
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 가입된 이메일 입니다."),
    PHONE_NUMBER_DUFILCATED(HttpStatus.CONFLICT, "이미 등록된 전화번호 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
