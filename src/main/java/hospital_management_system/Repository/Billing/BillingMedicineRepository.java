package hospital_management_sytem.Repository.Billing;

import hospital_management_sytem.Entity.Billing.BillingMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingMedicineRepository extends JpaRepository<BillingMedicineEntity,Long> {
}
