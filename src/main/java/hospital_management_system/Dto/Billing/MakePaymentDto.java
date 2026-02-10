package hospital_management_sytem.Dto.Billing;

import hospital_management_sytem.Enum.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentDto {
    @NotNull(message = "Billing id required")
    private Long billingId;
    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;
}
