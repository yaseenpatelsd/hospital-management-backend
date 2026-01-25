package hospital_management_sytem.Repository.Prescription;

import hospital_management_sytem.Entity.Prescription.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,Long> {

    Optional<PrescriptionEntity> findByAppointmentEntity_Id(Long id);
}
