package hospital_management_sytem.Dto.PrescriptionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponseDto {

    private Long prescriptionId;
    private Long appointmentId;


    private List<PrescriptionMedicineDto> medicineDtos;

    private Boolean isCompleted;
    private Boolean valid;

    private LocalDateTime createdAt;
}
