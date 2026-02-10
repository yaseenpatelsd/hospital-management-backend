package hospital_management_sytem.Entity.Billing;

import hospital_management_sytem.Entity.AppointmentEntity;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Entity.Profile.PatientProfile;
import hospital_management_sytem.Enum.BillStatus;
import hospital_management_sytem.Enum.PaymentMode;
import hospital_management_sytem.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billing")
public class BillingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal consultationFee;
    @Column(nullable = false)
    private BigDecimal medicineTotalPrice;

    @Column(nullable = false)
    private BigDecimal subTotalAmount;

    @Column(nullable = false)
    private BigDecimal taxAmount;

    @Column(nullable = false)
    private  BigDecimal finalPayableAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode=PaymentMode.NONE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus=PaymentStatus.UNPAID;


    private String invoiceNumber;
    private LocalDate billingDate;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id",nullable = false)
    private AppointmentEntity appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_Profile_id", nullable = false)
    private PatientProfile patientProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_Profile_id", nullable = false)
    private DoctorProfile doctorProfile;

    @OneToMany(
            mappedBy = "billingEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
     private List<BillingMedicineEntity> billingMedicine;

    private BillStatus status;
    private String voidReason;
    private LocalDateTime voidTime;

    @PrePersist
    public void onCreated(){
        this.billingDate=LocalDate.now();
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdated(){
        this.updatedAt=LocalDateTime.now();
    }
}
