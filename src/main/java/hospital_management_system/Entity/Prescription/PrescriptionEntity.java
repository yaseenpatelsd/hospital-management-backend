package hospital_management_sytem.Entity.Prescription;

import hospital_management_sytem.Entity.AppointmentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Prescription")
public class PrescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id",nullable = false)
    private AppointmentEntity appointmentEntity;

    @OneToMany(
            mappedBy = "prescriptionEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PrescriptionMedicineEntity> prescriptionMedicineEntity;

    private Boolean isComplete=false;
    private Boolean valid=true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void onCreated(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdated(){
        this.updatedAt=LocalDateTime.now();
    }
}
