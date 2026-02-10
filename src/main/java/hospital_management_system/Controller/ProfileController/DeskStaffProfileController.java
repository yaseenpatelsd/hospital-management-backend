package hospital_management_sytem.Controller.ProfileController;

import hospital_management_sytem.Dto.Profile.*;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import hospital_management_sytem.Service.ProfileService.StaffProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class DeskStaffProfileController {

    private final StaffProfileService staffProfileService;

    public DeskStaffProfileController(StaffProfileService staffProfileService) {
        this.staffProfileService = staffProfileService;
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/profile")
    public ResponseEntity<StaffResponseDto> addProfile(@Valid @RequestBody StaffRequestDto dto, Authentication authentication){
        StaffResponseDto staffResponseDto=staffProfileService.createProfile(authentication,dto);
        return ResponseEntity.ok(staffResponseDto);
    }
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/profile")
    public ResponseEntity<StaffResponseDto> getProfile( Authentication authentication){
        StaffResponseDto dto=staffProfileService.getProfile(authentication);
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/profile")
    public ResponseEntity<StaffResponseDto> editProfile(@Valid @RequestBody StaffEditRequestDto dto, Authentication authentication) {
        StaffResponseDto dto1 = staffProfileService.editProfile(authentication, dto);
        return ResponseEntity.ok(dto1);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/profile")
    public ResponseEntity<?> DeleteProfile ( Authentication authentication){
        staffProfileService.deleteProfile(authentication);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/profile/duty/on")
    public ResponseEntity<?> loginDuty( Authentication authentication){
        staffProfileService.logInDuty(authentication);
        return ResponseEntity.ok("welcome to work have a good day");
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/profile/duty/off")
    public ResponseEntity<?> logOutOfDuty( Authentication authentication){
        staffProfileService.logOutDuty(authentication);
        return ResponseEntity.ok("log out successfully thanks for your hard word");
    }
    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/profile/recovery")
    public ResponseEntity<?> recoverProfile( Authentication authentication){
        staffProfileService.recoverAccount(authentication);
        return ResponseEntity.ok("Account recover");
    }



}
