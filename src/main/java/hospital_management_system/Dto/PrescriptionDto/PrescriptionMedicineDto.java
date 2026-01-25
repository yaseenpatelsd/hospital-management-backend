package hospital_management_sytem.Dto.PrescriptionDto;

import hospital_management_sytem.Enum.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicineDto {


    private String medicineCode;
    private String name;
    private String brandName;
    private MedicineType category;
    private String strength;


    private Integer quantity;
    private String dosage;
    private String frequency;
    private String duration;
}
