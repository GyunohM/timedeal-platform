package capstone.dto.v2.seller;

import capstone.domain.v2.seller.SellerV2;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Builder //테스트 코드에서 .builder()를 쓰려면 달아줘야 함
@AllArgsConstructor // @Builder를 클래스에 달 때 반드시 함께 달아줘야 에러가 안남
public class SellerV2LocalSignupRequestDto {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 50, message = "이름은 2글자에서 50글자 사이입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Size(min = 10, max = 11, message = "전화번호는 10~11자리여야 합니다.")
    @Pattern(regexp = "^[0-9]*$", message = "전화번호는 숫자만 입력 가능합니다.")
    private String phoneNumber;

    private LocalDate birthday;

    @Size(max = 255, message = "주소는 255자를 초과할 수 없습니다.")
    private String address;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.") //로컬과 쇼셜을 구분하였고 로컬이기에 @NotBlank -> null 허용x
    @Size(min = 5, max = 255, message = "비밀번호는 최소 5글자 이상이어야 합니다.")
    private String password;

    //DTO -> Entity 변환 toEntity 패턴 도입 (서비스 계층에서 dto로 변환 로직을 옮김)
    public SellerV2 toEntity(String encodedPassword) {
        return SellerV2.builder()
            .name(this.getName())
            .phoneNumber(this.getPhoneNumber())
            .birthday(this.getBirthday())
            .address(this.getAddress())
            .email(this.getEmail())
            .password(encodedPassword) //원본 비번 대신 암호화된 비번 주입
            .build();
    }

}
