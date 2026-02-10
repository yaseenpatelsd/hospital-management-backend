package hospital_management_sytem.Service.ProfileService;

import hospital_management_sytem.Dto.Profile.DoctorProfileEditDto;
import hospital_management_sytem.Dto.Profile.DoctorProfileRequestDto;
import hospital_management_sytem.Dto.Profile.DoctorProfileResponseDto;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Exeption.ResourceNotAvailable;
import hospital_management_sytem.Exeption.SomethingIsWrong;
import hospital_management_sytem.Exeption.UserNotFound;
import hospital_management_sytem.Mapping.ProfileMapping.DoctorProfileMapping;
import hospital_management_sytem.Repository.ProfileRepository.DoctorProfileRepository;
import hospital_management_sytem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorProfileService {
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    public DoctorProfileService(UserRepository userRepository, DoctorProfileRepository doctorProfileRepository) {
        this.userRepository = userRepository;
        this.doctorProfileRepository = doctorProfileRepository;
    }



    @Transactional
    public DoctorProfileResponseDto createProfile(Authentication authentication, DoctorProfileRequestDto dto){
        UserEntity user=findUser(authentication);

        if (doctorProfileRepository.existsByUserAndIsActiveTrue(user)){
            throw new SomethingIsWrong("Doctor profile already exists");
        }
        DoctorProfile doctorProfile=DoctorProfileMapping.toEntity(dto);
        doctorProfile.setUser(user);
        doctorProfile.setIsActive(true);
        DoctorProfile saved=doctorProfileRepository.save(doctorProfile);

        return DoctorProfileMapping.toResponseDto(doctorProfile);
    }


    public DoctorProfileResponseDto getProfile(Authentication authentication){
        UserEntity user=findUser(authentication);

        DoctorProfile doctorProfile=findDoctorProfile(user);


        return DoctorProfileMapping.toResponseDto(doctorProfile);
    }

    @Transactional
    public DoctorProfileResponseDto editProfile(Authentication authentication, DoctorProfileEditDto dto){
        UserEntity user=findUser(authentication);

        DoctorProfile profile=findDoctorProfile(user);

        if (dto.getFullName()!=null){
            profile.setFullName(dto.getFullName());
        }
        if (dto.getGender()!=null){
            profile.setGender(dto.getGender());
        }
        if (dto.getMobileNo()!=null){
            profile.setMobileNo(dto.getMobileNo());
        }
        if (dto.getDegree()!=null){
            profile.setDegree(dto.getDegree());
        }
        if (dto.getSpecialization()!=null){
            profile.setSpecialization(dto.getSpecialization());
        }
        if (dto.getExperience()!=null){
            profile.setExperience(dto.getExperience());
        }
        if (dto.getConsultationFee()!=null){
            profile.setConsultationFee(dto.getConsultationFee());
        }
        if (dto.getOnDuty()!=null){
            profile.setOnDuty(dto.getOnDuty());
        }
        if (dto.getIsAvailable()!=null){
            profile.setIsAvailable(dto.getIsAvailable());
        }

        doctorProfileRepository.save(profile);

        return DoctorProfileMapping.toResponseDto(profile);
    }

    @Transactional
    public void deActivateProfile(Authentication authentication){
        UserEntity user=findUser(authentication);

        DoctorProfile profile=findDoctorProfile(user);

        if(profile.getIsActive().equals(false)) {
            profile.setIsActive(false);
        }

        doctorProfileRepository.save(profile);
    }

    @Transactional
    public void activeProfile(Authentication authentication){
        UserEntity user=findUser(authentication);

        DoctorProfile profile=ActiveDoctorProfile(user);

        profile.setIsActive(true);

        doctorProfileRepository.save(profile);
    }

    @Transactional
    public void logInDuty(Authentication authentication){
        UserEntity user=findUser(authentication);
        DoctorProfile profile=findDoctorProfile(user);

        profile.setOnDuty(true);

        doctorProfileRepository.save(profile);
    }
    @Transactional
    public void logOutDuty(Authentication authentication){
        UserEntity user=findUser(authentication);
        DoctorProfile profile=findDoctorProfile(user);

        profile.setOnDuty(false);

        doctorProfileRepository.save(profile);
    }

    @Transactional
    public void isAvailable(Authentication authentication) {
        UserEntity user = findUser(authentication);
        DoctorProfile profile = findDoctorProfile(user);

        profile.setIsAvailable(true);

        doctorProfileRepository.save(profile);
    }

    @Transactional
    public void busy(Authentication authentication){
        UserEntity user=findUser(authentication);
        DoctorProfile profile=findDoctorProfile(user);

        profile.setIsAvailable(false);

        doctorProfileRepository.save(profile);
    }

    @Transactional
    public List<DoctorProfileResponseDto> availableDoctors(){
        List<DoctorProfile> doctorProfiles=doctorProfileRepository.findByIsAvailableTrueAndIsActiveTrue();

        if (doctorProfiles.isEmpty()){
            throw new ResourceNotAvailable("No available doctor at this moment");
        }
        return doctorProfiles.stream().map(DoctorProfileMapping::toResponseDto).collect(Collectors.toList());
    }



/*----------------------------------------------------------------------------------------------------
                                         HElPER METHODS
-----------------------------------------------------------------------------------------------------*/


    public UserEntity findUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFound("Can't find a account"));
    }
    public DoctorProfile findDoctorProfile(UserEntity user){
        return doctorProfileRepository.findByUserAndIsActiveTrue(user)
                .orElseThrow(()-> new UserNotFound("Can't find a profile"));
    }

    public DoctorProfile ActiveDoctorProfile(UserEntity user){
        return doctorProfileRepository.findByUser(user)
                .orElseThrow(()-> new UserNotFound("Can't find a profile"));
    }
}
