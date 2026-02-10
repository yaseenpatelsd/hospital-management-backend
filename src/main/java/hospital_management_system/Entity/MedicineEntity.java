package hospital_management_sytem.Entity;

import hospital_management_sytem.Enum.MedicineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicine")
public class MedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,updatable = false)
    private String medicineCode;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String brandName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicineType category;
    @Column(nullable = false)
    private String strength;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stockQuantity;
    @Column(nullable = false)
    private Boolean isActive=true;
    @Column(nullable = false)
    private LocalDate expiryDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void onCreated(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

}
