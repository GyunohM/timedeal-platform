package capstone.repository.v2.user;

import capstone.domain.v2.user.UserV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserV2Repository extends JpaRepository<UserV2, Long> {


}
