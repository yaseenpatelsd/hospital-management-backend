package hospital_management_sytem.Dto.AppointmentDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentConfirmDto {
    private Long drId;
    private long appointmentId;
}
