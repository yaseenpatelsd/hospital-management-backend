package hospital_management_sytem.Controller;

import hospital_management_sytem.Dto.PrescriptionDto.DeletePrescription;
import hospital_management_sytem.Dto.PrescriptionDto.PrescriptionEditDto;
import hospital_management_sytem.Dto.PrescriptionDto.PrescriptionRequestDto;
import hospital_management_sytem.Dto.PrescriptionDto.PrescriptionResponseDto;
import hospital_management_sytem.Service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/create")
    public ResponseEntity<PrescriptionResponseDto> createPrescription(
            Authentication authentication,
            @Valid @RequestBody PrescriptionRequestDto dto
    ) {
        return ResponseEntity.ok(
                prescriptionService.createPrescription(authentication, dto)
        );
    }


    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/invalid")
    public ResponseEntity<?> invalid(
            Authentication authentication,
            @Valid @RequestBody DeletePrescription dto
    ) {
       prescriptionService.invalid(authentication ,dto);
       return ResponseEntity.ok("Prescription Id "+ dto.getPrescriptionId()+" "+"is mark is invalid");

    }
@PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/complete")
    public ResponseEntity<?> completePrescriptoon(
            Authentication authentication,
            @Valid @RequestBody DeletePrescription dto
    ) {
    prescriptionService.markPrescriptionComplete(authentication, dto);
        return ResponseEntity.ok("Mark as complete");
    }





    @GetMapping("{prescriptionId}")
    public ResponseEntity<PrescriptionResponseDto> editPrescription(Authentication authentication,@PathVariable Long prescriptionId){
        PrescriptionResponseDto responseDto=prescriptionService.getPrescription(authentication,prescriptionId);
        return ResponseEntity.ok(responseDto);
    }



}
