package capstone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    // 동네콕 도메인에서 오는 요청을 안전하게 허락함 (CORS 해결)
    @CrossOrigin(origins = "https://dongnekok.shop", methods = {RequestMethod.GET, RequestMethod.HEAD})
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        // DB를 거치지 않고 바로 대답하므로 서버가 켜지자마자 0.1초 만에 응답함
        return ResponseEntity.ok("OK");
    }
}
