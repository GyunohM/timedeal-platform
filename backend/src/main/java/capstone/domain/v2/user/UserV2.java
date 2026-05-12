package capstone.domain.v2.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Users_v2")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//그냥 id인데 컬럼에 user_id로 name을 해도 되나?
public class UserV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    //@Pattern(regexp = "^[가-힣a-zA-Z]*$")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, length = 20)
    private UserV2Status status;

    //
    public UserV2(String name, UserV2Status status){
        this.name = name;
        this.status = (status != null) ? status : UserV2Status.ACTIVE;
    }

    public void ChangeName(String newName){
        this.name = name;
    }

}
