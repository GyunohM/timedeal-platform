package capstone.config.v2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigV2 {
    @Bean //단방향 암호화 메소드 bean 등록해야 서비스계층에서 사용할 수 있음
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //config v2버전은 다시 수정할 것 26년 6월 7일
    @Bean
    @Order(1) // 기존 V1 Config(보통 Order가 없음)보다 무조건 먼저 검사하라고 1번 번호표를 줍니다.
    public SecurityFilterChain filterChainV2(HttpSecurity http) throws Exception {
        http
            // "/api/v2/"로 시작하는 요청만 이 V2 수위 아저씨가 낚아챕니다
            // (v1 요청이 들어오면 "내 관할 아니네?" 하고 기존 V1 Config로 넘겨버립니다)
            .securityMatcher("/api/v2/**")

            // JWT나 토큰 기반이므로 CSRF, Form 로그인 다 꺼줍니다.
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 권한 설정
            .authorizeHttpRequests(auth -> auth
                // V2 판매자 로컬 회원가입 주소는 프리패스 (로그인 없이 접근 가능)
                .requestMatchers("/api/v2/sellers/**").permitAll()
                // 그 외 V2의 다른 API들은 일단 막아둠
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
