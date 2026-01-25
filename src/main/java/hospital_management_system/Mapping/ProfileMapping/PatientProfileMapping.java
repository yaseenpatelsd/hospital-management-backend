package hospital_management_sytem.Mapping.ProfileMapping;

import hospital_management_sytem.Dto.Profile.PatientProfileRequestDto;
import hospital_management_sytem.Dto.Profile.PatientProfileResponseDto;
import hospital_management_sytem.Entity.Profile.PatientProfile;
import org.springframework.stereotype.Component;


@Component
public class PatientProfileMapping {

    public static PatientProfile toEntity(PatientProfileRequestDto dto){
        if (dto==null)return null;

        PatientProfile entity=new PatientProfile();
        entity.setFullName(dto.getFullName());
        entity.setGender(dto.getGender());
        entity.setDob(dto.getDob());
        entity.setMobileNo(dto.getMobileNo());
        entity.setAddress(dto.getAddress());

        return entity;
    }


    public static PatientProfileRequestDto requestDto(PatientProfile entity){
        if (entity==null)return null;

        PatientProfileRequestDto dto=new PatientProfileRequestDto();
        dto.setFullName(entity.getFullName());
        dto.setGender(entity.getGender());
        dto.setDob(entity.getDob());
        dto.setMobileNo(entity.getMobileNo());
        dto.setAddress(entity.getAddress());

        return dto;
    }

    public static PatientProfileResponseDto responseDto(PatientProfile entity){
        if (entity==null)return null;

        PatientProfileResponseDto dto=new PatientProfileResponseDto();
        dto.setFullName(entity.getFullName());
        dto.setGender(entity.getGender());
        dto.setDob(entity.getDob());
        dto.setMobileNo(entity.getMobileNo());
        dto.setAddress(entity.getAddress());

        return dto;
    }

}
