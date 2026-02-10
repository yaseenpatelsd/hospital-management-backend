package hospital_management_sytem.Entity.Billing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billing_medicine")
public class BillingMedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineCode;
    private String medicineName;
    private String brandName;

    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id", nullable = false)
    private BillingEntity billingEntity;

}
