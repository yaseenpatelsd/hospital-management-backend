package hospital_management_sytem.Dto.Profile;

import hospital_management_sytem.Enum.GlobalGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfileRequestDto {
    @NotBlank(message = "full Name is required for profile")
   private String fullName;
    @NotNull(message = "gender is required for profile")
    private GlobalGender gender;
    @NotNull(message = "dob is required for profile")
    private LocalDate dob;
    @NotBlank(message = "mobile number is required for profile")
    private String mobileNo;
    @NotBlank(message = "address is required for profile")
    private String address;
}
