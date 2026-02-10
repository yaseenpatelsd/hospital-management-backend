package hospital_management_sytem.Entity;

import hospital_management_sytem.Enum.GlobalRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private GlobalRole role;


    private String otp;
    private Long otpExpireTime;
    private Boolean isActive;

}
