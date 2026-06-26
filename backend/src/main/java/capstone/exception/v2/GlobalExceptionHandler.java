package capstone.exception.v2;


import capstone.dto.v2.common.ErrorResponseDto;
import capstone.dto.v2.common.FieldErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//GlobalExceptionHandler가 없었으면 자동 내장 예외 핸들러를 쓰기에 RestApiException 파일은 있어봤자 원하는 상태코드대신 500을 내보냄
//409를 내보낼 수 있지만 그럼 결국 컨트롤러에서 try catch 문을 쓸 수 밖에 없음
//프론트엔드에게 '최종적으로 에러객체를 전달'하는 역할
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //[직접 만든 RestApiException 커스텀 예외 처리]
    //GlobalExceptionHandler가 RestApiException을 여기서 낚아챔
    // ErrorCode 객체에 담긴 값을 빼서 프론트엔드로 보내기 위해 ErrorResponseDto에 담아 보냄
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponseDto> handleRestApiException(RestApiException ex){
        ErrorCode errorCode = ex.getErrorCode();

        log.warn("Custom Business Exception 발생 -> Code:{}, Message:{}" , errorCode.name(),errorCode.getMessage());

        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(ErrorResponseDto.of(errorCode));
    }

    //[프론트엔드 입력값 검증 예외 처리]
    //컨트롤러의 @Valid 검증에서 에러가 나면 스프링이 MethodArgumentNotValidException을 던지는데 그 예외를 처리
    //매번 다른 컨트롤러 안에서 if(bindingResult.hasErrors()) 안해도 됨
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex){
        log.warn("Validation Exception 발생 -> 유효성 검사 실패");

        BindingResult bindingResult = ex.getBindingResult();
        List<FieldErrorDto> errorDetails = new ArrayList<>();
        // 어느 필드(칸)에서 무슨 에러 메시지가 났는지 리스트에 다 담아둠
        for(FieldError error : bindingResult.getFieldErrors()){
            errorDetails.add(new FieldErrorDto(error.getField(), error.getDefaultMessage()));
        }
        // 정적 팩토리 메서드(of) 중 '상세 입력값 에러 리스트'를 같이 받는 오버로딩 메서드 호출
        return ResponseEntity
            .status(ErrorCode.BAD_REQUEST.getHttpStatus())
            .body(ErrorResponseDto.of(ErrorCode.BAD_REQUEST, errorDetails));

    }

    //[알 수 없는 서버 내부 에러 처리]
    //예상하지 못한 자바 기본 에러를 잡아내고 원인을 알아야 고치니까 log.error로 스택 트레이스를 다 남김
    //프론트엔드(유저)에게는 에러의 자체를 보여주기 보단 500 에러로 깔끔히 보낼 예정
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllException(Exception ex){
        log.error("예상치 못한 시스템 발생!! 원인: ",ex);

        return ResponseEntity
            .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
            .body(ErrorResponseDto.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }



}
