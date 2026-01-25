package hospital_management_sytem.Repository.ProfileRepository;

import hospital_management_sytem.Entity.Profile.DeskStaffProfile;
import hospital_management_sytem.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<DeskStaffProfile,Long> {

    Optional<DeskStaffProfile> findByUserAndOnDutyTrue(UserEntity user);
    Optional<DeskStaffProfile> findByUser(UserEntity user);
    Optional<DeskStaffProfile> findByStaffId(String staffId);

    Boolean existsByUser(UserEntity user);

}
