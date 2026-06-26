package capstone.controller.v2.seller;


import capstone.dto.v2.seller.SellerV2LocalSignupRequestDto;
import capstone.service.v2.seller.SellerV2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러가 프론트엔드에 json으로 자동변환해서 응답
@RequestMapping("/api/v2/sellers") //대표 주소
@RequiredArgsConstructor //final이 붙은 걸 생성자 자동으로 만들어줌
public class SellerV2Controller {
    private final SellerV2Service sellerV2Service;

    @PostMapping("/signup/local") //post 요청이 들어오는 상세 주소 url (post 등록)
    //ResponseEntity< > -> 컨트롤러의 메서드에 붙는 반환타입은 100프로 이 형식
    //<?> -> 와일드 카드 문법 사용 - 일단 빠르게 코드 완성한 후 추후 공통응답 dto 만들고 와일드 카드 삭제 예정
    public ResponseEntity<?> signupLocalSeller(
        //@vaild는 dto 파일에 달린 유효성 검사 어노테이션을 자동실행 @RequestBody는 프론트엔드에서 json으로 넘어오는데 이를 dto로 자동 변환
        @Valid @RequestBody SellerV2LocalSignupRequestDto requestDto){
            sellerV2Service.signupLocalSeller(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("판매자 로컬 회원가입이 성공적으로 완료되었습니다.");

    }
}
