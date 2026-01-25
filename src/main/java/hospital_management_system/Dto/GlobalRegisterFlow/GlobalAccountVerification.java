package hospital_management_sytem.Dto.GlobalRegisterFlow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalAccountVerification {
    @NotBlank(message = "email is required for account registration")
    private String email;
    @NotBlank(message = "Otp is required")
    @Size(min = 6,max = 6,message = "OTP should be 6 number")
    private String otp;
}
