package hospital_management_sytem.Dto.Billing;

import hospital_management_sytem.Enum.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingRequestDto {
    @NotNull
    private Long appointmentId;
    @NotNull
    private Long prescriptionId;
}
