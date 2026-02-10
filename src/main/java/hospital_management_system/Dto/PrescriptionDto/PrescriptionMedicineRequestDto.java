package hospital_management_sytem.Dto.PrescriptionDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicineRequestDto {
    @NotNull
    private String medicineCode;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String dosage;

    @NotBlank
    private String frequency;

    @NotBlank
    private String duration;
}
