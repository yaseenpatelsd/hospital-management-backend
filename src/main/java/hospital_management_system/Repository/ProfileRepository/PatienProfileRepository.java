package hospital_management_sytem.Repository.ProfileRepository;

import hospital_management_sytem.Entity.Profile.PatientProfile;
import hospital_management_sytem.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PatienProfileRepository extends JpaRepository<PatientProfile ,Long> {
    Optional<PatientProfile> findByUserAndIsActiveTrue(UserEntity user);
    Optional<PatientProfile> findByUser(UserEntity user);

    Boolean existsByUserAndIsActiveTrue(UserEntity user);
}
