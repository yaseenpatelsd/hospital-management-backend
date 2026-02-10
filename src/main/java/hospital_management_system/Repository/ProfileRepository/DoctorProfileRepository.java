package hospital_management_sytem.Repository.ProfileRepository;

import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile,Long> {

    Optional<DoctorProfile> findByUserAndIsActiveTrue(UserEntity user);
    Optional<DoctorProfile> findByUser(UserEntity user);
    Boolean existsByUserAndIsActiveTrue(UserEntity user);


    List<DoctorProfile> findByIsAvailableTrueAndIsActiveTrue();

}
