package hospital_management_sytem.Dto.Billing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponseDto {

        private Long appointmentId;
        private String invoiceNumber;
        private List<BillingMedicineResponseDto> medicine;

}
