package hospital_management_sytem.Mapping;

import hospital_management_sytem.Dto.AppointmentDto.AppointmentRequestDto;
import hospital_management_sytem.Dto.AppointmentDto.AppointmentResponseDto;
import hospital_management_sytem.Entity.AppointmentEntity;

public class AppointmentMapper {

    public static AppointmentEntity toEntity(AppointmentRequestDto dto){
        if (dto==null)return null;

        AppointmentEntity entity=new AppointmentEntity();
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setReason(dto.getReason());

        return entity;
    }

    public static AppointmentRequestDto toRequestDto(AppointmentEntity entity){
        if (entity==null)return null;


        AppointmentRequestDto dto=new AppointmentRequestDto();
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setReason(entity.getReason());

        return dto;
    }

    public static AppointmentResponseDto toResponseDto(AppointmentEntity entity){
        if (entity==null)return null;


        AppointmentResponseDto dto=new AppointmentResponseDto();
        dto.setId(entity.getId());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setStatus(entity.getStatus());
        dto.setReason(entity.getReason());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getDoctorProfile()!=null){
            dto.setDoctorName(entity.getDoctorProfile().getFullName());
        }

        if (entity.getPatientProfile()!=null){
            dto.setPatientName(entity.getPatientProfile().getFullName());
        }
        return dto;
    }
}
