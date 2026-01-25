package hospital_management_sytem.Dto.Profile;

import hospital_management_sytem.Enum.GlobalGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDto {
    @NotBlank(message = "Staff Id is required")
    @Pattern(
            regexp = "^STAFF-[0-9]{4}$",
            message = "Staff ID must be like STAFF-1234"
    )
    private String staffId;
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotNull(message = "gender is required")
    private GlobalGender gender;
    @NotBlank(message = "Mobile no is required ")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number"
    )
    private String mobileNo;
    @NotNull(message = "onDuty is required")
    private boolean onDuty;
    @NotBlank(message = "Address is required")
    private String address;
}
