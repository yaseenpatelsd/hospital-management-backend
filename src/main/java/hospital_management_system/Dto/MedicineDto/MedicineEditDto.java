package hospital_management_sytem.Dto.MedicineDto;

import hospital_management_sytem.Enum.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineEditDto {
    private String name;
    private String brandName;
    private MedicineType category;
    private String strength;
    private BigDecimal price;
    private Integer stockQuantity;
    private LocalDate expiryDate;
}
