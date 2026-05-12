package capstone.domain.v2.seller;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table( name = "Seller_v2",
    uniqueConstraints = {
    @UniqueConstraint(
        name = "unique_social_login",
        columnNames = {"social_login_id", "social_provider"}
        )
    }
)
public class SellerV2 {

    @Id
    //컬럼 어노테이션에는 실제 컬럼명이 들어간다.
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, length = 50)
    @Size(min = 2, max = 50)
    private String name;


    @Column(name = "phone_number", nullable = false, length = 11, unique = true)
    private String phoneNumber;

    //sql에서 date가 java에서는 Localdate임
    private LocalDate birthday;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    // @NotBlank -> null을 허용하지 않겠다는 말인데 우리는 null 허용이므로 이거 빼야함
    @Size(min = 5 , max = 255)
    @Column(name = "password", nullable = true, length = 255)
    //@Pattern()
    private String password;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SellerV2Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private SellerV2Role role;

    @Column(name = "social_login_id", length = 255)
    private String socialLoginId;

    @Column( name = "social_provider", length = 30)
    private String socialProvider;


//생성자에 id는 왜 안넣지 -> id는 순서대로 생성해서 넣어주는 어노테이션을 붙였으므로 오히려 생성자를 넣으면 잘못된 값이 들어갈 수 있어서 뺸거
//가능한 한 많은 정보를 포함한 생성자를 도메인에 만들어두면 좋다.
//생성자에 빌더 어노테이션을 달아둬야 서비스 계층에서 파라미터의 순서 개수에 관계없이 빌더 패턴으로 객체인스턴스를 생성가능
    //생성자는 퍼블릭이어야 외부에서 객체를 생성할 수 있음
    @Builder
    public SellerV2(String name, String phoneNumber, LocalDate birthday, String address, String email, String password, SellerV2Status status, SellerV2Role role, String socialLoginId, String socialProvider) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.password = password;
        this.status = (status != null) ? status : SellerV2Status.ACTIVE;
        this.role = (role != null) ? role : SellerV2Role.ROLE_USER;
        this.socialLoginId= socialLoginId;
        this.socialProvider= socialProvider;
    }
    //객체 내부의 필드만 가지고 행위를 할 수 있는 메소드는 서비스계층보다 도메인에 적어두면 서비스 계층이 깔끔해진다.
    //나중에 도메인에 넣을 메소드 추가 하기
}

