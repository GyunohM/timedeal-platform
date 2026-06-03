package capstone.dto.v2;

import capstone.dto.v2.seller.SellerV2LocalSignupRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class SellerV2LocalSignupRequestDtoTest {

    //static은 클래스 전체에서 딱 1개만 만들어서 공용으로 사용한다는 의미
    private static final String VALID_NAME = "인어남";
    private static final String VALID_PHONE = "01012345678";
    private static final LocalDate VALID_BIRTHDAY = LocalDate.of(2020,4,5);
    private static final String VALID_ADDRESS = "서울시 강남구";
    private static final String VALID_EMAIL = "test@test.com";
    private static final String VALID_PASSWORD = "password123";

    //Validator라는 타입의 데이터를 담을 수 있는 validator변수
    //검사관이 들어갈 빈 공간'만 만들어 둔 것입니다. 아직 검사관은 없음
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        //자바 내부에서 복잡한 검사관들을 찍어내는 '공장'을 세우는 것
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //공장에서 검사관을 뽑아서 자리에 앉힘
        validator = factory.getValidator();

    };

    //var를 쓰는 이유는 지역 변수 타입 추론임 원래대로 해도 되고 현업에서는 var 많이 씀
    @Test
    @DisplayName("전화번호에 하이픈이 들어가고 자릿수가 초과되면 관련된 모든 검증 에러가 발생해야 한다")
    //전화번호 실패 테스트
    void phoneNumber_fail_test(){
        var dto = createVaildDtoBuilder()
            .phoneNumber("010-1234-5678")
            .build();

        //ConstraintViolation은 제약 조건 위반. 프론트엔드가 글자 수나 이메일 양식을 어겼을 때 발부되는 에러 티켓
        //validate(dto): Validator 객체 안에 원래 만들어져 있는 고정 메서드. 이 DTO 좀 들여다보라는 의미
        //Set: 자바의 자료구조(List, Map 등) 중 하나. 중복을 허용하지 않는 자료구조
        Set<ConstraintViolation<SellerV2LocalSignupRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        //Set은 List와 달리 순서가 없는 뒤죽박죽 주머니라서 .get(0)이라는 문법 자체가 존재하지 않음.
        //set에 있는 모든 에러 딱지에서 getMessage() 결과만 쏙쏙 추출(extracting)해서 한 줄로 세워라! 그 후순서는 상관없으니(InAnyOrder), 내가 적은 에러 메시지 2개가 있어야 성공
        assertThat(violations)
            .extracting(ConstraintViolation::getMessage)
            .containsExactlyInAnyOrder("전화번호는 숫자만 입력 가능합니다.","전화번호는 10~11자리여야 합니다.");
    }

    //.build(); // 여기서 build()를 해서 완제품을 만들어버리면 안됨 아직 완성되지 않은 뼈대(Builder) 상태"로 반환을 해줘야, 위쪽 테스트 코드에서 뼈대를 넘겨받은 뒤 .phoneNumber("이상한번호")를 추가로 덮어씌우고 마지막에 .build()를 해서 객체를 완성할 수 있음
        private SellerV2LocalSignupRequestDto.SellerV2LocalSignupRequestDtoBuilder createVaildDtoBuilder() {
            return SellerV2LocalSignupRequestDto.builder()
                .name(VALID_NAME)
                .phoneNumber(VALID_PHONE)
                .email(VALID_EMAIL)
                .password(VALID_PASSWORD)
                .birthday(VALID_BIRTHDAY)
                .address(VALID_ADDRESS);
        }
}
