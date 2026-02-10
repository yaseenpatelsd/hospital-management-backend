package hospital_management_sytem.Controller;

import hospital_management_sytem.Dto.GlobalRegisterFlow.*;
import hospital_management_sytem.Service.UserService.GlobalRegisterFlow;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient/auth")
public class PatientController {
    private final GlobalRegisterFlow globalRegisterFlow;

    public PatientController(GlobalRegisterFlow globalRegisterFlow) {
        this.globalRegisterFlow = globalRegisterFlow;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody GlobalRegisterDto dto){
        globalRegisterFlow.patientRegister(dto);
        return ResponseEntity.ok("Patient is register please verify account with otp sand to your email");
    }

    @PostMapping("/account/verification")
    public ResponseEntity<?> accountVerification(@Valid @RequestBody GlobalAccountVerification dto){
        globalRegisterFlow.account_verification(dto);
        return ResponseEntity.ok("Patient account is verified");
    }

    @PostMapping("/otp/request/for/account/verification")
    public ResponseEntity<?> otpRequestForAccountVerification(@Valid @RequestBody GlobalOtpRequestDto dto){
        globalRegisterFlow.otpRequestForAccountVerification(dto);
        return ResponseEntity.ok("otp is sand again on your email");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody GlobalLogInDto dto){
        return ResponseEntity.ok( globalRegisterFlow.login(dto));
    }

    @PostMapping("/request/otp")
    public ResponseEntity<?> requestOtp(@Valid @RequestBody GlobalOtpRequestDto dto){
        globalRegisterFlow.requestOtpForPasswordReset(dto);
        return ResponseEntity.ok("Otp is sanded to your email");
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> passwordReset(@Valid @RequestBody GlobalPasswordResetDto dto){
        globalRegisterFlow.passwordReset(dto);
        return ResponseEntity.ok("Password is reset you can log in using new password");
    }

}
