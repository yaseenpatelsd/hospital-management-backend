package hospital_management_sytem.Dto.Profile;

import hospital_management_sytem.Enum.GlobalGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileResponseDto {
    private String fullName;
    private GlobalGender gender;
    private String mobileNo;
    private String degree;
    private String specialization;
    private Integer experience;
    private Integer consultationFee;
    private Boolean onDuty;
    private Boolean isAvailable;
}
