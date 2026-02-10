package hospital_management_sytem.Mapping.ProfileMapping;

import hospital_management_sytem.Dto.Profile.StaffRequestDto;
import hospital_management_sytem.Dto.Profile.StaffResponseDto;
import hospital_management_sytem.Entity.Profile.DeskStaffProfile;

public class DeskStaffMapper {

    public static DeskStaffProfile toEntity(StaffRequestDto dto){
        if (dto==null)return null;

        DeskStaffProfile entity=new DeskStaffProfile();

        entity.setStaffId(dto.getStaffId());
        entity.setFullName(dto.getFullName());
        entity.setGender(dto.getGender());
        entity.setMobileNo(dto.getMobileNo());
        entity.setOnDuty(dto.isOnDuty());
        entity.setAddress(dto.getAddress());

        return entity;
    }

    public static StaffRequestDto toRequestDto(DeskStaffProfile entity){
        if (entity==null)return null;

        StaffRequestDto dto=new StaffRequestDto();

        dto.setStaffId(entity.getStaffId());
        dto.setFullName(entity.getFullName());
        dto.setGender(entity.getGender());
        dto.setMobileNo(entity.getMobileNo());
        dto.setAddress(entity.getAddress());
        dto.setOnDuty(entity.isOnDuty());

        return dto;
    }

    public static StaffResponseDto toResponseDto(DeskStaffProfile entity){
        if (entity==null)return null;

        StaffResponseDto dto=new StaffResponseDto();

        dto.setStaffId(entity.getStaffId());
        dto.setFullName(entity.getFullName());
        dto.setGender(entity.getGender());
        dto.setMobileNo(entity.getMobileNo());
        dto.setAddress(entity.getAddress());
        dto.setOnDuty(entity.isOnDuty());
        dto.setAdminApproved(entity.isAdminApproved());

        return dto;
    }
}
