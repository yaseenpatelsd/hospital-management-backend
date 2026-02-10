package hospital_management_sytem.Controller;

import hospital_management_sytem.Dto.AppointmentDto.*;
import hospital_management_sytem.Service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@Valid @RequestBody AppointmentRequestDto dto, Authentication authentication){
        AppointmentResponseDto appointmentResponseDto=appointmentService.bookAppointment(authentication,dto);
        return ResponseEntity.ok(appointmentResponseDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/confirm")
    public ResponseEntity<AppointmentResponseDto> confirmAppointmentAndAssigningDoctor(@Valid @RequestBody AppointmentConfirmDto dto,Authentication authentication){
        AppointmentResponseDto appointmentResponseDto=appointmentService.confirmAppointmentAndAppointADoctor(authentication,dto);
        return ResponseEntity.ok(appointmentResponseDto);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/cancel")
    public ResponseEntity<AppointmentResponseDto> cancelByPatient(@Valid @RequestBody AppointmentCancelDto dto,Authentication authentication){
        AppointmentResponseDto appointmentResponseDto=appointmentService.cancelAppointmentByPatient(authentication,dto);
        return ResponseEntity.ok(appointmentResponseDto);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/cancel/ByDoctor")
    public ResponseEntity<AppointmentResponseDto> cancelByDoctor(@Valid @RequestBody AppointmentCancelDto dto,Authentication authentication){
        AppointmentResponseDto appointmentResponseDto=appointmentService.cancelAppointmentByDoctor(authentication,dto);
        return ResponseEntity.ok(appointmentResponseDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/cancel/byStaff")
    public ResponseEntity<AppointmentResponseDto> cancelByStaff(@Valid @RequestBody AppointmentCancelDto dto,Authentication authentication){
        AppointmentResponseDto appointmentResponseDto=appointmentService.cancelAppointmentByStaff(authentication,dto);
        return ResponseEntity.ok(appointmentResponseDto);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/successful")
    public ResponseEntity<AppointmentSucessResponseDto> appointmentSuccessful(@Valid @RequestBody AppointmentSucessRequestDto dto,Authentication authentication){
        AppointmentSucessResponseDto appointmentSucessResponseDto=appointmentService.successfulAppointment(authentication,dto);
        return ResponseEntity.ok(appointmentSucessResponseDto);
    }


}
