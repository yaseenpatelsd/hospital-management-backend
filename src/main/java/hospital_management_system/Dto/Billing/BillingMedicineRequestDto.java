package hospital_management_sytem.Dto.Billing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingMedicineRequestDto {
    @NotBlank(message = "Required Field")
    private String medicineCode;

    @NotNull(message = "Required Field")
    private Integer quantity;
}

