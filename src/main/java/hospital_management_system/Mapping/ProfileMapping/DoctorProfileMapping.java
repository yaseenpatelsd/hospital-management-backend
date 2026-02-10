package hospital_management_sytem.Mapping.ProfileMapping;

import hospital_management_sytem.Dto.Profile.DoctorProfileRequestDto;
import hospital_management_sytem.Dto.Profile.DoctorProfileResponseDto;
import hospital_management_sytem.Entity.Profile.DoctorProfile;
import org.springframework.stereotype.Component;

@Component
public class DoctorProfileMapping {

    public static DoctorProfile toEntity(DoctorProfileRequestDto dto){
        if(dto==null)return null;

        DoctorProfile entity=new DoctorProfile();
        entity.setFullName(dto.getFullName());
        entity.setGender(dto.getGender());
        entity.setMobileNo(dto.getMobileNo());
        entity.setDegree(dto.getDegree());
        entity.setSpecialization(dto.getSpecialization());
        entity.setExperience(dto.getExperience());
        entity.setConsultationFee(dto.getConsultationFee());


        return entity;
    }

    public static DoctorProfileRequestDto toRequestDto(DoctorProfile entity){
        if(entity==null)return null;

        DoctorProfileRequestDto dto=new DoctorProfileRequestDto();
        dto.setFullName(entity.getFullName());
        dto.setGender(entity.getGender());
        dto.setMobileNo(entity.getMobileNo());
        dto.setDegree(entity.getDegree());
        dto.setSpecialization(entity.getSpecialization());
        dto.setExperience(entity.getExperience());
        dto.setConsultationFee(entity.getConsultationFee());


        return dto;
    }


    public static DoctorProfileResponseDto toResponseDto(DoctorProfile entity){
        if(entity==null)return null;

        DoctorProfileResponseDto dto=new DoctorProfileResponseDto();
        dto.setFullName(entity.getFullName());
        dto.setGender(entity.getGender());
        dto.setMobileNo(entity.getMobileNo());
        dto.setDegree(entity.getDegree());
        dto.setSpecialization(entity.getSpecialization());
        dto.setExperience(entity.getExperience());
        dto.setConsultationFee(entity.getConsultationFee());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setOnDuty(entity.getOnDuty());


        return dto;
    }





}
