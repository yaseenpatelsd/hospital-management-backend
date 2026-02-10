package hospital_management_sytem.Service.UserService;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFound("Can't find a account link to the username"));

       return User.builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .roles(user.getRole().name())
               .build();
    }
}
