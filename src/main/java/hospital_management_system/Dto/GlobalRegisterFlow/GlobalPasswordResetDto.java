package hospital_management_sytem.Dto.GlobalRegisterFlow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalPasswordResetDto {
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;
    @NotBlank(message = "otp required")
    @Size(min = 6,max = 6,message = "otp must be atLeast 6 digits")
    private String otp;
}
