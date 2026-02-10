package hospital_management_sytem.Service.ProfileService;

import hospital_management_sytem.Dto.Profile.StaffEditRequestDto;
import hospital_management_sytem.Dto.Profile.StaffRequestDto;
import hospital_management_sytem.Dto.Profile.StaffResponseDto;
import hospital_management_sytem.Entity.Profile.DeskStaffProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Mapping.ProfileMapping.DeskStaffMapper;
import hospital_management_sytem.Repository.ProfileRepository.StaffRepository;
import hospital_management_sytem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StaffProfileService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public StaffProfileService(UserRepository userRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public StaffResponseDto createProfile(Authentication authentication, StaffRequestDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        if (staffRepository.existsByUser(user)){
            throw new SomethingIsWrong("Found profile link to your account");
        }

        DeskStaffProfile profile= DeskStaffMapper.toEntity(dto);
        profile.setUser(user);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        DeskStaffProfile saved=staffRepository.save(profile);

        return DeskStaffMapper.toResponseDto(saved);
    }

    public StaffResponseDto getProfile(Authentication authentication){
        UserEntity user=findUserByAuthentication(authentication);

        ifAccountIsDisable(user);

        DeskStaffProfile profile=findStaff(user);
        confirmStaff(profile);
        return DeskStaffMapper.toResponseDto(profile);
    }

    @Transactional
    public StaffResponseDto editProfile(Authentication authentication, StaffEditRequestDto dto){
        UserEntity user=findUserByAuthentication(authentication);

        DeskStaffProfile profile=findStaff(user);

        ifAccountIsDisable(user);

        if (dto.getFullName()!=null){
            profile.setFullName(dto.getFullName());
        }
        if (dto.getGender()!=null){
            profile.setGender(dto.getGender());
        }
        if (dto.getMobileNo()!=null){
            profile.setMobileNo(dto.getMobileNo());
        }
        if (dto.getAddress()!=null){
            profile.setAddress(dto.getAddress());
        }

        profile.setUpdatedAt(LocalDateTime.now());
        DeskStaffProfile saved= staffRepository.save(profile);
        return DeskStaffMapper.toResponseDto(saved);
    }
    @Transactional
    public void logInDuty(Authentication authentication){
        UserEntity user=findUserByAuthentication(authentication);

        DeskStaffProfile profile=findStaff(user);

        ifAccountIsDisable(user);

        if (profile.isOnDuty()){
            throw new SomethingIsWrong("Already on duty");
        }

        confirmStaff(profile);

        profile.setOnDuty(true);
        profile.setUpdatedAt(LocalDateTime.now());

        staffRepository.save(profile);
    }
    @Transactional
    public void logOutDuty(Authentication authentication){
        UserEntity user=findUserByAuthentication(authentication);

        DeskStaffProfile profile=findStaff(user);

        ifAccountIsDisable(user);

        if (!profile.isOnDuty()){
            throw new SomethingIsWrong("Already log out");
        }

        profile.setOnDuty(false);
        profile.setUpdatedAt(LocalDateTime.now());

        staffRepository.save(profile);
    }


    public void deleteProfile(Authentication authentication){
        UserEntity user=findUserByAuthentication(authentication);

        DeskStaffProfile deskStaffProfile=findStaff(user);

        deskStaffProfile.setIsActive(false);

        staffRepository.save(deskStaffProfile);
    }

    public void recoverAccount(Authentication authentication){
        UserEntity user=findUserByAuthentication(authentication);

        DeskStaffProfile deskStaffProfile=findStaff(user);

        if (deskStaffProfile.getIsActive()){
            throw new SomethingIsWrong("Account is already active");
        }

        deskStaffProfile.setIsActive(true);

        staffRepository.save(deskStaffProfile);

    }
    /*--------------------------------------------------------------------------------------------------------
                                           HELPING METHODS
    ---------------------------------------------------------------------------------------------------------*/

    public UserEntity findUserByAuthentication(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFound("Can't find a Account!"));
    }

    public DeskStaffProfile findStaff(UserEntity user){
        return staffRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotAvailable("Can't find profile"));
    }
    public void confirmStaff(DeskStaffProfile profile){
        if (!profile.isAdminApproved()){
            throw new SomethingIsWrong("Not valid till admin approved it");
        }
    }
    public void ifAccountIsDisable(UserEntity user){
        DeskStaffProfile deskStaffProfile=findStaff(user);

        if (deskStaffProfile.getIsActive().equals(false)){
            throw new SomethingIsWrong("Ooh it seems like account is deleted for recovery please go to recovery page");
        }
    }
}
