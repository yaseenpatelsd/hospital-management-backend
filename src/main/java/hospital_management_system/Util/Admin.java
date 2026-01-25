package hospital_management_sytem.Util;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Admin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
       Boolean isAdmin=userRepository.existsByRole(GlobalRole.ADMIN);

        if (!isAdmin){
            UserEntity user=new UserEntity();
            user.setUsername("ADMIN");
            user.setPassword(passwordEncoder.encode("ADMIN"));
            user.setRole(GlobalRole.ADMIN);
            user.setIsActive(true);
            user.setEmail("yaseenpatel238@gmail.com");

            userRepository.save(user);
        }
    }

}
