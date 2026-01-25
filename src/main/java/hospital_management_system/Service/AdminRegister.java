package hospital_management_sytem.Service;

import hospital_management_sytem.Dto.GlobalRegisterFlow.GlobalLogInDto;
import hospital_management_sytem.Dto.ResponseToken;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Exeption.InvalidDetails;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Jwt.JwtUtil;
import hospital_management_sytem.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminRegister {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AdminRegister(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }



    public ResponseToken login(GlobalLogInDto dto) {

        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(()->new UserNotFound("ADMIN NOT FOUND"));

        if (user.getIsActive().equals(false)){
            throw new SomethingIsWrong("Please verify account before you try to log in");
        }

        Boolean admin=false;
        if (dto.getUsername().equals("admin") && dto.getPassword().equals("admin")){
            admin=true;
        }else {
            throw new SomethingIsWrong("Password wont match");
        }


            String token = jwtUtil.generateToken(user.getUsername());
            return new ResponseToken(token);

    }
}
