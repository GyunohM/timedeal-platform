package capstone.repository.v2.seller;

import capstone.domain.v2.seller.SellerV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerV2Repository extends JpaRepository<SellerV2, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

}
