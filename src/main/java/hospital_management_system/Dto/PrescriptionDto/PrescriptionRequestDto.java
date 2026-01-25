package hospital_management_sytem.Dto.PrescriptionDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionRequestDto {
    @NotNull
    private Long appointmentId;

    @NotEmpty
    private List<PrescriptionMedicineRequestDto> medicines;
}
