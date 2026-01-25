package hospital_management_sytem.Repository;

import hospital_management_sytem.Entity.MedicineEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<MedicineEntity,Long> {

     Optional<MedicineEntity> findByMedicineCode(String medicineCode);

     List<MedicineEntity> findAllByIsActiveTrue();
}
