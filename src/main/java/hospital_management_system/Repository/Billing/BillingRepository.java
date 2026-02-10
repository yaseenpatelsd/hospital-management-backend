package hospital_management_sytem.Repository.Billing;

import hospital_management_sytem.Entity.Billing.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<BillingEntity,Long> {

    Optional<BillingEntity> findByAppointment_id(Long id);

    Boolean existsByAppointment_Id(Long id);
}
