package hospital_management_sytem.Service;

import hospital_management_sytem.Dto.AdminDoctorVerification;
import hospital_management_sytem.Dto.AdminStaffVerification;
import hospital_management_sytem.Entity.Profile.DeskStaffProfile;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Repository.ProfileRepository.DoctorProfileRepository;
import hospital_management_sytem.Repository.ProfileRepository.StaffRepository;
import hospital_management_sytem.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminControllPanelService {
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final StaffRepository staffRepository;


    public AdminControllPanelService(UserRepository userRepository, DoctorProfileRepository doctorProfileRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.staffRepository = staffRepository;
    }

    public void verifyDoctor(Authentication authentication, AdminDoctorVerification adminDoctorVerification){
        UserEntity user=findUserByAuthentication(authentication);
        roleCheck(user,GlobalRole.ADMIN);

        DoctorProfile profile=findDoctor(adminDoctorVerification);

        if (profile.getIsApprovedByAdmin()){
            throw new SomethingIsWrong("Profile is verified Already");
        }

        if (!profile.getIsActive()){
            throw new SomethingIsWrong("Profile is disable");
        }

        profile.setIsApprovedByAdmin(true);

        doctorProfileRepository.save(profile);
    }

    public void verifyStaff(Authentication authentication, AdminStaffVerification adminStaffVerification){
        UserEntity user=findUserByAuthentication(authentication);
        roleCheck(user,GlobalRole.ADMIN);

        DeskStaffProfile profile=findStaff(adminStaffVerification);

        if (profile.isAdminApproved()){
            throw new SomethingIsWrong("Already verified");
        }

        profile.setAdminApproved(true);
        profile.setCreatedAt(LocalDateTime.now());

        staffRepository.save(profile);
    }




    /*-----------------------------------------------------------------------------------------------------
                                              HELPER METHOD
     -----------------------------------------------------------------------------------------------------*/

    public UserEntity findUserByAuthentication(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFound("User not found"));
    }
    public DoctorProfile findDoctor(AdminDoctorVerification adminDoctorVerification){
        return doctorProfileRepository.findById(adminDoctorVerification.getId())
                .orElseThrow(()->new ResourceNotAvailable("Profile not exist"));
    }

    public DeskStaffProfile findStaff(AdminStaffVerification adminStaffVerification){
        return staffRepository.findByStaffId(adminStaffVerification.getId())
                .orElseThrow(()->new ResourceNotAvailable("Profile not exist"));
    }

    public void roleCheck(UserEntity user, GlobalRole role){
        if (user.getRole()!=role){
            throw new SomethingIsWrong("Not Authorized");
        }
    }
}
