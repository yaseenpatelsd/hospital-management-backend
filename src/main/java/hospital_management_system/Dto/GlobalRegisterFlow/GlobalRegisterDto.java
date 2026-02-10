package hospital_management_sytem.Dto.GlobalRegisterFlow;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalRegisterDto {
    @NotBlank(message = "username can't be blank")
    private String username;
    @NotBlank(message = "password can't be blank")
    private String password;
    @NotBlank(message = "email can't be blank")
    private String email;
}
