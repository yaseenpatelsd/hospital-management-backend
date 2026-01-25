package hospital_management_sytem.Dto.AppointmentDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDto {
    @NotNull(message = "appointmentDate is required")
    private LocalDate appointmentDate;
    @NotNull(message = "startedAt is required")
    private LocalTime startTime;
    @NotNull(message = "endedAt is required")
    private LocalTime endTime;
    @NotBlank
    private String reason;

}
