package hospital_management_sytem.Entity.Prescription;

import hospital_management_sytem.Entity.MedicineEntity;
import hospital_management_sytem.Entity.Prescription.PrescriptionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescription_medicine")
public class  PrescriptionMedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String dosage;
    @Column(nullable = false)
    private String frequency;
    @Column(nullable = false)
    private String duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id",nullable = false)
    private PrescriptionEntity prescriptionEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id",nullable = false)
    private MedicineEntity medicineEntity;



}
