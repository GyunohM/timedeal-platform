package capstone.service.v2;

import capstone.domain.v2.seller.SellerV2;
import capstone.dto.v2.seller.SellerV2LocalSignupRequestDto;
import capstone.repository.v2.seller.SellerV2Repository;
import capstone.service.v2.seller.SellerV2Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerV2ServiceTest {

    @InjectMocks
    private SellerV2Service sellerV2Service;

    @Mock
    private SellerV2Repository sellerV2Repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String VALID_NAME = "인어남";
    private static final String VALID_PHONE = "01012345678";
    private static final LocalDate VALID_BIRTHDAY = LocalDate.of(1995, 1, 1);
    private static final String VALID_ADDRESS = "서울시";
    private static final String VALID_EMAIL = "test@test.com";
    private static final String VALID_PASSWORD = "password123";


    private SellerV2LocalSignupRequestDto.SellerV2LocalSignupRequestDtoBuilder createValidDto(){
        return SellerV2LocalSignupRequestDto.builder()
            .name(VALID_NAME)
            .phoneNumber(VALID_PHONE)
            .email(VALID_EMAIL)
            .password(VALID_PASSWORD)
            .birthday(VALID_BIRTHDAY)
            .address(VALID_ADDRESS);
    }

    @Test
    @DisplayName("이메일과 전화번호가 둘 다 중복되지 않고 비밀번호가 암호화가 되면(가짜 객체 mock의 행동임 실제 중복테스트 및 암호화 x) 회원가입에 성공하므로 db에 save(저장) 요청 로직이 실행되어야 함")
    void signupLocalSeller_success_test(){

        //given : 정상적 객체 생성 및 가짜 객체 (행동) 설정
        var dto = createValidDto().build();
        //이메일과 전화번호가 중복되지 않다고 설정
        given(sellerV2Repository.existsByEmail(anyString())).willReturn(false);
        given(sellerV2Repository.existsByPhoneNumber(anyString())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("암호화된 비밀번호 덩어리");

        //when : 서비스 로직 실행
        sellerV2Service.signupLocalSeller(dto);

        //then : db 저장 코드 줄이 딱 1번 실행되었는지 물어봄
        verify(sellerV2Repository, times(1)).save(any(SellerV2.class));

    }
@Test
@DisplayName("이메일이 이미 존재하여 중복된다고 mock (가짜 객체에게 설정)하여 이메일 이미 존재하면 IllegalArgumentException이 터져야 한다.")
    void signupLocalSeller_fail_duplicate_email(){
        //given
        //고의로 이메일 중복시킴
        String duplicateEmail = "duplicatetest@test.com";

        var dto = createValidDto()
            .email(duplicateEmail)
            .build();
        //이메일이 이미 존재하여 중복된다고 설정
        given(sellerV2Repository.existsByEmail(duplicateEmail)).willReturn(true);

        //when & then
        assertThatThrownBy(()->sellerV2Service.signupLocalSeller(dto)).isInstanceOf(IllegalArgumentException.class).hasMessage("이미 가입된 이메일 입니다.");
        //db 저장(service의 @Transactional 로직 전체) 되지 않아야한다.
        verify(sellerV2Repository, never()).save(any(SellerV2.class));

    }
@Test
@DisplayName("전화번화가 이미 존재하여 중복된다고 mock (가짜 객체에게 설정)하여 전화번호가 이미 존재하면 IllegalArgumentException이 터지고 db에 저장되지 않아야한다.")
    void signupLocalSeller_fail_duplicate_phone(){
        //given
        //고의로 전화번호를 중복시키기 위해 변수 생성
        String duplicatePhone = "01099998888";

        var dto = createValidDto()
            .phoneNumber(duplicatePhone)
            .build();

        //전화번호가 이미 존재하여 중복된다고 가정
        given(sellerV2Repository.existsByPhoneNumber(duplicatePhone)).willReturn(true);

        //when & then
        assertThatThrownBy(()->sellerV2Service.signupLocalSeller(dto)).isInstanceOf(IllegalArgumentException.class).hasMessage("이미 등록된 전화번호 입니다.");

        //db 저장(service의 @Transactional 로직 전체) 되지 않아야한다.
        verify(sellerV2Repository,never()).save(any(SellerV2.class));

    }
}
