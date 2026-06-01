package capstone.repository.v2.seller;

import capstone.domain.v2.seller.SellerV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // db 테스트 어노테이션
@ActiveProfiles("test") //application-test.yml 설정을 불러옴
@EntityScan(basePackages = "capstone.domain.v2") //v2 도메인 패키지만 읽음
@EnableJpaRepositories(basePackages = "capstone.repository.v2") //v2 레포지토리만 읽음
class SellerV2RepositoryTest {

    @Autowired
    private SellerV2Repository sellerV2Repository;

    @Test
    @DisplayName("seller 객체를 db에 저장 그리고 id로 다시 조회")
    void saveAndFindTest(){

        //given (객체가 주어짐)

        String name = "균오";
        String email ="test@test.com";

        //when 객체 생성
        SellerV2 seller = SellerV2.builder()
            .name(name)
            .email(email)
            .phoneNumber("01012345678") //-(하이푼)들어가면 11글자 초과해서 안됨. 테스트 중 발견.
            .address("경기 안성시")
            .socialLoginId("1234ABC")
            .socialProvider("NAVER")
            .build();

        //when
        //db 저장은 여기서! 빌더는 db저장까지 하지 않는다.
        SellerV2 savedSeller = sellerV2Repository.save(seller);
        // id로 찾기
        SellerV2 foundSeller = sellerV2Repository.findById(savedSeller.getId()).get();

        //then(검증)
        assertThat(foundSeller.getId()).isNotNull(); //db에 저장했기 때문에 id가 자동할당
        assertThat(foundSeller.getName()).isEqualTo(name);
        assertThat(foundSeller.getEmail()).isEqualTo(email);
    }
}
