package hospital_management_sytem.Dto.Profile;

import hospital_management_sytem.Enum.GlobalGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffEditRequestDto {
    private String fullName;
    private GlobalGender gender;
    private String mobileNo;
    private boolean onDuty;
    private String address;
}
