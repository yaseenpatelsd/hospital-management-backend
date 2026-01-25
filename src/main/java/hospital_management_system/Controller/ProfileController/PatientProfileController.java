package hospital_management_sytem.Controller.ProfileController;

import hospital_management_sytem.Dto.Profile.PatientProfileEditDto;
import hospital_management_sytem.Dto.Profile.PatientProfileRequestDto;
import hospital_management_sytem.Dto.Profile.PatientProfileResponseDto;
import hospital_management_sytem.Service.ProfileService.PatientProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientProfileController {
    private final PatientProfileService patientProfileService;
    public PatientProfileController(PatientProfileService patientProfileService) {
        this.patientProfileService = patientProfileService;
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/profile")
    public ResponseEntity<PatientProfileResponseDto> addProfile(@Valid  @RequestBody PatientProfileRequestDto dto, Authentication authentication){
        PatientProfileResponseDto patientProfileResponseDto=patientProfileService.creatingProfile(dto,authentication);
        return ResponseEntity.ok(patientProfileResponseDto);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/profile/recover")
    public ResponseEntity<?> recover( Authentication authentication){
      patientProfileService.activeProfile(authentication);
        return ResponseEntity.ok("Prole is back if you want to change something you can do it in edit section");
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/profile")
    public ResponseEntity<PatientProfileResponseDto> getProfile( Authentication authentication){
        PatientProfileResponseDto patientProfileResponseDto=patientProfileService.getPatientProfile(authentication);
        return ResponseEntity.ok(patientProfileResponseDto);
    }
    @PreAuthorize("hasRole('PATIENT')")
    @PatchMapping("/profile")
    public ResponseEntity<PatientProfileResponseDto> editProfile(@Valid @RequestBody PatientProfileEditDto dto,Authentication authentication){
        PatientProfileResponseDto patientProfileResponseDto=patientProfileService.editing(authentication,dto);
        return ResponseEntity.ok(patientProfileResponseDto);
    }
    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping("/profile")
    public ResponseEntity<?> delete(Authentication authentication){
        patientProfileService.delete(authentication);
        return ResponseEntity.noContent().build();
    }
}
