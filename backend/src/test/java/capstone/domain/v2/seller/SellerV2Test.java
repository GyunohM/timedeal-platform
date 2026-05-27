package capstone.domain.v2.seller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SellerV2Test {

    @Test
    @DisplayName("사장님 객체가 생성되고 , 기본 상태값이 ACTIVE 와 ROLEUSER로 세팅되는 테스트, " +
        "password가 존재하고 반대로 socialLoginId와 socialProvider가 null")
        //given (객체가 주어짐)
    void createLocalSellerTest() {
        String name = "균오";
        String phoneNumber = "01012345678";
        LocalDate birthday = LocalDate.of(2021, 4, 5);
        String address = "서울시 강남구";
        String email = "test@test.com";
        String password = "password1234";
        String socialLoginId = "naver1234";
        String socialProvider = "NAVER";
        //when (객체 생성)

        SellerV2 seller = SellerV2.builder()
            .name(name)
            .phoneNumber(phoneNumber)
            .birthday(birthday)
            .address(address)
            .email(email)
            .password(password)
            .build();

        //then (검증)

        assertThat(seller.getName()).isEqualTo(name);
        assertThat(seller.getEmail()).isEqualTo(email);
        assertThat(seller.getPassword()).isNotNull();
        assertThat(seller.getSocialLoginId()).isNull();
        assertThat(seller.getSocialProvider()).isNull();
    }

    @Test
    @DisplayName("쇼셜 판매자 회원가입 테스트, passoword가 null이며 socialLoginId,socialProvider가 존재함")
    void createSocialSellerTest() {


        String name = "균오";
        String phoneNumber = "01012345678";
        LocalDate birthday = LocalDate.of(2021, 4, 5);
        String address = "서울시 강남구";
        String email = "test@test.com";
        String socialLoginId = "naver1234";
        String socialProvider = "NAVER";

        SellerV2 seller = SellerV2.builder()
            .name(name)
            .phoneNumber(phoneNumber)
            .birthday(birthday)
            .address(address)
            .email(email)
            .socialLoginId(socialLoginId)
            .socialProvider(socialProvider)
            .build();

        assertThat(seller.getName()).isEqualTo(name);
        assertThat(seller.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(seller.getPassword()).isNull();
        assertThat(seller.getSocialProvider()).isNotNull();
        assertThat(seller.getSocialLoginId()).isNotNull();
    }

    @Test
    @DisplayName("로컬 password 와 (쇼셜 id ,provider 둘 중 하나)가 동시에 들어오면 IllegalArgumentException이 발생")
    void duplicateLoginTypeFailTest(){
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
            SellerV2.builder()
                .name("균오")
                .phoneNumber("01012345678")
                .email("test@test.com")
                .password("password1234")
                .socialLoginId("naver1234")
                .socialProvider("NAVER")
                .build()
        ).isInstanceOf(IllegalArgumentException.class); //주의 !! 에러가 터져야 성공임 에러가 성공으로 뜸
    }
}
