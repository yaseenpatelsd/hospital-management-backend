package hospital_management_sytem.Entity.Profile;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "desk_staff_profile")
public class DeskStaffProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlobalGender gender;
    @Column(nullable = false)
    private String mobileNo;
    @Column(nullable = false)
    private boolean onDuty=false;
    @Column(nullable = false)
    private String address;

    @Column(nullable = false ,unique = true)
    private String staffId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean adminApproved=false;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    private UserEntity user;


    private Boolean isActive=true;

}
