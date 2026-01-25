package hospital_management_sytem.Controller;

import hospital_management_sytem.Dto.MedicineDto.MedicineEditDto;
import hospital_management_sytem.Dto.MedicineDto.MedicineRequestDto;
import hospital_management_sytem.Dto.MedicineDto.MedicineResponseDto;
import hospital_management_sytem.Service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicine")
public class MedicineController {
    private final MedicineService medicineService;
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }
    @PreAuthorize("hasRole('STAFF')")
    @PostMapping
    public ResponseEntity<MedicineResponseDto> createMedicine(Authentication authentication, @Valid @RequestBody MedicineRequestDto dto){
        MedicineResponseDto responseDto=medicineService.createMedicine(authentication,dto);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/{medicineCode}")
    public ResponseEntity<MedicineResponseDto> editMedicine(Authentication authentication, @PathVariable String medicineCode, @Valid @RequestBody MedicineEditDto dto){
        MedicineResponseDto responseDto=medicineService.editMedicine(authentication,medicineCode,dto);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{medicineCode}")
    public ResponseEntity<?> deleteMedicine(Authentication authentication,@PathVariable String medicineCode){
        medicineService.delete(authentication,medicineCode);
        return ResponseEntity.noContent().build();
    }

    // anyone can access those

    @GetMapping("/{medicineCode}")
    public ResponseEntity<MedicineResponseDto> getMedicine(@PathVariable String medicineCode){
      MedicineResponseDto responseDto=  medicineService.getMedicine(medicineCode);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<MedicineResponseDto>> getAllMedicine(){
        List<MedicineResponseDto> findAll=medicineService.getAllMedicine();
        return ResponseEntity.ok(findAll);
    }
}
