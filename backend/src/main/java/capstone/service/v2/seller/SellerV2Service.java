package capstone.service.v2.seller;

import capstone.domain.v2.seller.SellerV2;
import capstone.dto.v2.seller.SellerV2LocalSignupRequestDto;
import capstone.repository.v2.seller.SellerV2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerV2Service {
    //SellerV2Repository에게 직접 일을 시키면 안되고 SellerV2Repository 타입의 sellerV2Repository를 만들어 일을 시키는 것
    private final SellerV2Repository sellerV2Repository;
    //PasswordEncoder 메소드는 v2config 파일에서 @bean으로 등록해둔걸 인스턴스화 해서 사용하면 됨
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signupLocalSeller(SellerV2LocalSignupRequestDto dto){
        //중복 검증 -> 이 코드는 다른 db도 찾아봐야하므로 도메인계층이 아닌 서비스계층에 넣어야함
        //도메인은 자기자신에 대한 데이터관련 메소드 구현은 가능 다만 다른 유저 데이터를 알 권한이 없음
        //db에서 자동으로 변환해주지만 서비스 계층에서 이메일을 소문자로 강제변환 정규화 추가하는게 안전함 중복 검증 전에 꼭 해야함. 이후에 dto.toEntity에서도 변환해서 최종적으로 저장하는게 좋음
        String normalizedEmail = dto.getEmail().toLowerCase();
        if (sellerV2Repository.existsByEmail(normalizedEmail)){
            throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
        }
        if (sellerV2Repository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new IllegalArgumentException("이미 등록된 전화번호 입니다.");
        }
        //비밀번호 단방향 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        //toEntity 패턴으로 dto->entity 변환로직을 dto로 옮겨서 서비스계층에서는 한줄로 축소됨
        SellerV2 seller = dto.toEntity(encodedPassword);

        //저장
        sellerV2Repository.save(seller);
    }
}
