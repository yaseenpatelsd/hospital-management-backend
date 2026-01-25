package hospital_management_sytem.Service.ProfileService;

import hospital_management_sytem.Dto.Profile.PatientProfileEditDto;
import hospital_management_sytem.Dto.Profile.PatientProfileRequestDto;
import hospital_management_sytem.Dto.Profile.PatientProfileResponseDto;
import hospital_management_sytem.Entity.Profile.PatientProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Exeption.ResourceAlreadyAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Mapping.ProfileMapping.PatientProfileMapping;
import hospital_management_sytem.Repository.ProfileRepository.PatienProfileRepository;
import hospital_management_sytem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientProfileService {
    private final PatienProfileRepository patienProfileRepository;
    private final UserRepository userRepository;

    public PatientProfileService(PatienProfileRepository patienProfileRepository, UserRepository userRepository) {
        this.patienProfileRepository = patienProfileRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public PatientProfileResponseDto creatingProfile(PatientProfileRequestDto dto, Authentication authentication){
       UserEntity user=findUser(authentication);


       if (patienProfileRepository.existsByUserAndIsActiveTrue(user)){
           throw new ResourceAlreadyAvailable("Profile Already exist");
       }


        PatientProfile patientProfile=PatientProfileMapping.toEntity(dto);
       patientProfile.setUser(user);
       patientProfile.setCreatedAt(LocalDateTime.now());
       patientProfile.setIsActive(true);
        PatientProfile saved=patienProfileRepository.save(patientProfile);
        PatientProfileResponseDto output=PatientProfileMapping.responseDto(saved);

        return output;
    }

    public PatientProfileResponseDto getPatientProfile(Authentication authentication){
          UserEntity user=findUser(authentication);

          PatientProfile patientProfile=findProfile(user);

         return PatientProfileMapping.responseDto(patientProfile);
    }

    @Transactional
    public PatientProfileResponseDto editing(Authentication authentication, PatientProfileEditDto dto){
        UserEntity user=findUser(authentication);
        PatientProfile patientProfile=findProfile(user);

        if (dto.getFullName()!=null){
            patientProfile.setFullName(dto.getFullName());
        }
        if (dto.getGender()!=null){
            patientProfile.setGender(dto.getGender());
        }
        if (dto.getDob()!=null){
            patientProfile.setDob(dto.getDob());
        }
        if (dto.getMobileNo()!=null){
            patientProfile.setMobileNo(dto.getMobileNo());
        }
        if (dto.getAddress()!=null){
            patientProfile.setAddress(dto.getAddress());
        }

        patienProfileRepository.save(patientProfile);


        return PatientProfileMapping.responseDto(patientProfile);
    }

    @Transactional
    public void delete(Authentication authentication){
        UserEntity user=findUser(authentication);

        PatientProfile profile=findProfile(user);

        profile.setIsActive(false);

        patienProfileRepository.save(profile);
    }


    @Transactional
    public void activeProfile(Authentication authentication){
        UserEntity user=findUser(authentication);
        PatientProfile profile=findDeactiveProfile(user);

        if (profile.getIsActive()){
            throw new SomethingIsWrong("Account is already active");
        }
        profile.setIsActive(true);

        patienProfileRepository.save(profile);

    }

    /*------------------------------------------------------------------------------------------------------
                                        Helper methods
    --------------------------------------------------------------------------------------------------------*/

    public UserEntity findUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new UserNotFound("Account not found"));
    }

    public PatientProfile findProfile(UserEntity entity){
        return patienProfileRepository
                .findByUserAndIsActiveTrue(entity)
                .orElseThrow(() -> new UserNotFound("Active profile not found"));
    }
    public PatientProfile findDeactiveProfile(UserEntity entity){
        return patienProfileRepository
                .findByUser(entity)
                .orElseThrow(() -> new UserNotFound("Active profile not found"));
    }

}
