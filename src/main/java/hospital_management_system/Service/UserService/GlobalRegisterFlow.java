package hospital_management_sytem.Service.UserService;

import hospital_management_sytem.Dto.GlobalRegisterFlow.*;
import hospital_management_sytem.Dto.ResponseToken;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Exeption.*;
import hospital_management_sytem.Jwt.JwtUtil;
import hospital_management_sytem.Repository.UserRepository;
import hospital_management_sytem.Service.EmailService;
import hospital_management_sytem.Util.OtpGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalRegisterFlow {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final OtpGenerator otpGenerator;
    public GlobalRegisterFlow(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, EmailService emailService, OtpGenerator otpGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.otpGenerator = otpGenerator;
    }



    public void doctorRegister(GlobalRegisterDto dto){
      register(dto,GlobalRole.DOCTOR);
    }

    public void patientRegister(GlobalRegisterDto dto){
        register(dto,GlobalRole.PATIENT);
    }
    public void staffRegister(GlobalRegisterDto dto){
        register(dto,GlobalRole.STAFF);
    }

    public void otpRequestForAccountVerification(GlobalOtpRequestDto dto){
        UserEntity user=findUserByEmail(dto.getEmail());

        if (user.getIsActive()){
            throw new SomethingIsWrong("Account is already verified");
        }

        String otp=otpGenerator.otpGeneration();
        long expireDate=otpGenerator.otpExpire(10);

        //saving new otp
        user.setOtp(otp);
        user.setOtpExpireTime(expireDate);

        //email sand
        emailService.AccountVerification(user,otp);

        //saved
        userRepository.save(user);

    }

    public void account_verification(GlobalAccountVerification dto){
        UserEntity user=findUserByEmail(dto.getEmail());

        if(user.getIsActive()){
            throw new SomethingIsWrong("Account is already verified");
        }

        verifyOtp(user.getOtp(),dto.getOtp(),user.getOtpExpireTime());

        user.setIsActive(true);

        userRepository.save(user);
    }

    public ResponseToken login(GlobalLogInDto dto) {

        UserEntity user = findUser(dto.getUsername());

        if (user.getIsActive().equals(false)){
            throw new SomethingIsWrong("Please verify account before you try to log in");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
        } catch (Exception ex) {
            throw new InvalidDetails("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new ResponseToken(token);
    }


    public void requestOtpForPasswordReset(GlobalOtpRequestDto dto){
        UserEntity user=findUserByEmail(dto.getEmail());

        String otp=otpGenerator.otpGeneration();
        long expireDate=otpGenerator.otpExpire(10);

        //saving new otp
        user.setOtp(otp);
        user.setOtpExpireTime(expireDate);

        //email sand
        emailService.forPasswordReset(user,otp);

        userRepository.save(user);
    }


    public void passwordReset(GlobalPasswordResetDto dto){
        UserEntity user=findUserByEmail(dto.getEmail());

        verifyOtp(user.getOtp(), dto.getOtp(), user.getOtpExpireTime());


        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setOtp(null);
        user.setOtpExpireTime(null);

        userRepository.save(user);
    }




    /*-----------------------------------------------------------------------------------------------------
                                     Helper methhods
    ======================================================================================================= */

    public UserEntity findUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFound("Account not found By Username"));
    }

    public UserEntity findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFound("Account not found By email"));
    }

    public void verifyOtp(String storeOtp,String otp,Long expireOtp){

        if (otp==null ||storeOtp==null|| otp.isBlank()|| storeOtp.isBlank()){
            throw new OtpRelatedExeption("Something wrong with otp request and try again");
        }
        if (!storeOtp.equals(otp)){
            throw new OtpRelatedExeption("Otp is wrong");
        }
        if (expireOtp<System.currentTimeMillis()){
            throw new OtpRelatedExeption("Otp is expired");
        }
    }


    public void register(GlobalRegisterDto dto ,GlobalRole role){
        Optional<UserEntity> userEntityOptional=userRepository.findByUsername(dto.getUsername());

        if (userEntityOptional.isPresent()){
            throw new UserAlreadyExist("Username is already register with other account");
        }

        UserEntity user=new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRole(role);

        //otp generation
        String otp= otpGenerator.otpGeneration();
        long otpExpire= otpGenerator.otpExpire(10);

        //save otp
        user.setOtp(otp);
        user.setOtpExpireTime(otpExpire);
        user.setIsActive(false);

        emailService.AccountVerification(user,otp);

        userRepository.save(user);
    }
}
