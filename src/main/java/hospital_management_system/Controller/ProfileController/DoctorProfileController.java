package hospital_management_sytem.Controller.ProfileController;

import hospital_management_sytem.Dto.Profile.*;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Service.ProfileService.DoctorProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorProfileController {
    private final DoctorProfileService doctorProfileService;

    public DoctorProfileController(DoctorProfileService doctorProfileService) {
        this.doctorProfileService = doctorProfileService;
    }


    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile")
    public ResponseEntity<DoctorProfileResponseDto> addProfile(@Valid @RequestBody DoctorProfileRequestDto dto, Authentication authentication){
        DoctorProfileResponseDto patientProfileResponseDto=doctorProfileService.createProfile(authentication,dto);
        return ResponseEntity.ok(patientProfileResponseDto);
    }



    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/profile")
    public ResponseEntity<DoctorProfileResponseDto> getProfile( Authentication authentication){
        DoctorProfileResponseDto dto=doctorProfileService.getProfile(authentication);
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping("/profile")
    public ResponseEntity<DoctorProfileResponseDto> editProfile(@Valid @RequestBody DoctorProfileEditDto dto, Authentication authentication){
        DoctorProfileResponseDto dto1=doctorProfileService.editProfile(authentication,dto);
        return ResponseEntity.ok(dto1);
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping("/profile")
    public ResponseEntity<?> delete(Authentication authentication){
        doctorProfileService.deActivateProfile(authentication);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile/recover")
    public ResponseEntity<?> recover( Authentication authentication){
        doctorProfileService.activeProfile(authentication);
        return ResponseEntity.ok("Prole is back if you want to change something you can do it in edit section");
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile/duty/on")
    public ResponseEntity<?> loginDuty( Authentication authentication){
        doctorProfileService.logInDuty(authentication);
        return ResponseEntity.ok("welcome to work have a good day");
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile/duty/off")
    public ResponseEntity<?> logOutOfDuty( Authentication authentication){
        doctorProfileService.logOutDuty(authentication);
        return ResponseEntity.ok("log out successfully thanks for your hard word");
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile/status/available")
    public ResponseEntity<?> available( Authentication authentication){
        doctorProfileService.isAvailable(authentication);
        return ResponseEntity.ok("You are now available for patients");
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/profile/status/busy")
    public ResponseEntity<?> occupied( Authentication authentication){
        doctorProfileService.busy(authentication);
        return ResponseEntity.ok("You are now busy and hidden from patients");
    }


    @PostMapping("/findAll")
    public ResponseEntity<List<DoctorProfileResponseDto>> findAll(){
        List<DoctorProfileResponseDto> doctorProfileResponseDtos=doctorProfileService.availableDoctors();

        return ResponseEntity.ok(doctorProfileResponseDtos);
    }

}
