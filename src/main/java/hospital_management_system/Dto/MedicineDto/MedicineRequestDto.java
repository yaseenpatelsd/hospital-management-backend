package hospital_management_sytem.Dto.MedicineDto;

import hospital_management_sytem.Enum.MedicineType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequestDto {
    @NotBlank(message = "Medicine Code is required")
    private String medicineCode;
    @NotBlank(message = "Name is required")

    private String name;
    @NotBlank(message = "Brand Name is required")

    private String brandName;
    @NotNull(message = "Category is required")

    private MedicineType category;
    @NotBlank(message = "Strength is required")

    private String strength;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0",inclusive = false,message = "price must be greater than 0")
    private BigDecimal price;
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock quantity can't be negative")
    private Integer stockQuantity;
    @NotNull(message = "expire Date is required")
    @Future(message = "Expire Date must be in future")
    private LocalDate expiryDate;

}
