package hospital_management_sytem.Entity.Profile;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient_profile")
public class PatientProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name" ,nullable = false)
    private String fullName;
    @Column(name = "gender",nullable = false)
    private GlobalGender gender;
    @Column(name = "dob",nullable = false)
    private LocalDate dob;
    @Column(name = "mobileNo",nullable = false,unique = true)
    private String mobileNo;
    @Column(name = "createdAt",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "address",nullable = false)
    private String address;

    private Boolean isActive=false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
      nullable = false,
    unique = true)
    private UserEntity user;
}
