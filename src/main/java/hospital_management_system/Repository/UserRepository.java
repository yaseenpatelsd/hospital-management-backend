package hospital_management_sytem.Repository;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    Boolean existsByRole(GlobalRole role);


}
