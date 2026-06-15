package capstone.dto.v2.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

//에러 정보 전용 DTO
@Getter
@AllArgsConstructor
public class FieldErrorDto {
    private String field;
    private String message;
}
