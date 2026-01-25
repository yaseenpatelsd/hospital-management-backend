package hospital_management_sytem.Dto.Profile;

import hospital_management_sytem.Enum.GlobalGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponseDto {
    private String fullName;
    private GlobalGender gender;
    private String mobileNo;
    private boolean onDuty;
    private String address;
    private String staffId;
    private boolean adminApproved;
}
