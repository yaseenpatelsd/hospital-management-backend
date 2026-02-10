package hospital_management_sytem.Controller;


import hospital_management_sytem.Dto.AdminDoctorVerification;
import hospital_management_sytem.Dto.AdminStaffVerification;
import hospital_management_sytem.Dto.GlobalRegisterFlow.GlobalLogInDto;
import hospital_management_sytem.Service.AdminControllPanelService;
import hospital_management_sytem.Service.AdminRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminControl {
private final AdminRegister adminRegister;
private final AdminControllPanelService adminControllPanelService;

    public AdminControl(AdminRegister adminRegister, AdminControllPanelService adminControllPanelService) {
        this.adminRegister = adminRegister;
        this.adminControllPanelService = adminControllPanelService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(GlobalLogInDto dto){
        return ResponseEntity.ok(adminRegister.login(dto));
    }

    @PostMapping("/verify/doctor")
    public ResponseEntity<?> confirmDoctor(Authentication authentication, @RequestBody AdminDoctorVerification adv){
        adminControllPanelService.verifyDoctor(authentication,adv);
        return ResponseEntity.ok("Doctor is id "+ adv.getId() +" now doctor is officially a part of team");
    }

    @PostMapping("/verify/staff")
    public ResponseEntity<?> verifyStaff(Authentication authentication, @RequestBody AdminStaffVerification adminStaffVerification) {
        adminControllPanelService.verifyStaff(authentication, adminStaffVerification);
        return ResponseEntity.ok("staff id  "+ adminStaffVerification.getId() +" is now staff is officially a part of team");
    }
}
