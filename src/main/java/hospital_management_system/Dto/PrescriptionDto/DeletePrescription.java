package hospital_management_sytem.Dto.PrescriptionDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePrescription {
    @NotNull(message = "can't be null")
    @Min(value = 0,message = "Minimum value should be 0")
    private Long appointmentId;
    @NotNull(message = "can't be null")
    @Min(value = 0,message = "Minimum value should be 0")
    private Long prescriptionId;
}
