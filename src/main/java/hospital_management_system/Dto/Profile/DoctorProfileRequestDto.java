package hospital_management_sytem.Dto.Profile;

import hospital_management_sytem.Enum.GlobalGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileRequestDto {
    @NotBlank(message = "Full Name required for profile")
    private String fullName;
    @NotNull(message = "gender required for profile")
    private GlobalGender gender;
    @NotBlank(message = "mobile number required for profile")
    private String mobileNo;
    @NotBlank(message = "Degree  required for profile")
    private String degree;
    @NotBlank(message = "specialization required for profile")
    private String specialization;
    @NotNull(message = "experience required for profile")
    private Integer experience;
    @NotNull(message = "consultation Fees required for profile")
    private Integer consultationFee;

}
