package hospital_management_sytem.Dto.PrescriptionDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PrescriptionEditDto {
    @NotEmpty
    private List<PrescriptionMedicineRequestDto> medicines;
}
