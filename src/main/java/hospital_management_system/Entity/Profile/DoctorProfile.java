package hospital_management_sytem.Entity.Profile;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullName",nullable = false)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender",nullable = false)
    private GlobalGender gender;
    @Column(name = "mobileNo",nullable = false,unique = true)
    private String mobileNo;
    @Column(name = "degree",nullable = false)
    private String degree;
    @Column(name = "specialization",nullable = false)
    private String specialization;
    @Column(name = "experience",nullable = false)
    private Integer experience;
    @Column(name = "consultationFee",nullable = false)
    private Integer consultationFee;
    @Column(name = "isAvailable",nullable = false)
    private Boolean isAvailable=false;
    @Column(name = "onDuty",nullable = false)
    private  Boolean onDuty=false;

    @Column(name = "approvedbyAdmin")
    private Boolean isApprovedByAdmin=false;


    private Boolean isActive;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            unique = true,
            nullable = false)
    private UserEntity user;
}
