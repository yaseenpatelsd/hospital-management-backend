package hospital_management_sytem.Dto.AppointmentDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCancelDto {
    @NotNull(message = "appointment id is required")
    private Long appointmentId;
    private String cancelReason;
}
