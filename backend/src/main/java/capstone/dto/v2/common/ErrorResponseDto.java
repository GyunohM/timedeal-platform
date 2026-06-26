package capstone.dto.v2.common;

import capstone.exception.v2.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponseDto {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;


    @JsonInclude(JsonInclude.Include.NON_EMPTY) //필드가 비어있거나 null이면 JSON 응답창에서 아예 숨겨버림 - 프론트 엔드 배려용도
    private final List<FieldErrorDto> errors; // @Valid 방어막이 뚫렸을 때, 어느 입력 칸이 왜 틀렸는지 상세 목록 담는 배열

    //@Builder를 클래스 레벨에 달지말고 생성자 레벨에 닮 ( 이유 : 클래스 레벨에
    // @Builder 달고 timestamp에 Builder.Default(변경 될 수 있는 여지가 남아있음) 대신 timestamp는 아예 파라미터에서 제외하여 조작할 수 있는 문 자체를 없애기 위함)
    @Builder
    public ErrorResponseDto(int status, String error, String message, List<FieldErrorDto> errors){
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
    }

    //  [정적 팩토리 메서드 패턴] 일반 비즈니스 에러용 (중복 가입 등등)
    public static ErrorResponseDto of(ErrorCode errorCode){ //of : 정적 팩토리 메서드에서 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 메서드
        return ErrorResponseDto.builder()
            .status(errorCode.getHttpStatus().value())
            .error(errorCode.getHttpStatus().name())
            .message(errorCode.getMessage())
            .build();
    }
    // [정적 팩토리 메서드 패턴] @Valid 다중 검증 에러용도
    public static ErrorResponseDto of(ErrorCode errorCode, List<FieldErrorDto> errors){
        return ErrorResponseDto.builder()
            .status(errorCode.getHttpStatus().value())
            .error(errorCode.getHttpStatus().name())
            .message(errorCode.getMessage())
            .errors(errors) // DTO 유효성 실패 상세 리스트(배열)를 적재
            .build();
    }
}
