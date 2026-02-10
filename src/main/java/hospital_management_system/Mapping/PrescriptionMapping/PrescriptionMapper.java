package hospital_management_sytem.Mapping.PrescriptionMapping;

import hospital_management_sytem.Dto.PrescriptionDto.PrescriptionMedicineDto;
import hospital_management_sytem.Dto.PrescriptionDto.PrescriptionResponseDto;
import hospital_management_sytem.Entity.Prescription.PrescriptionEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionMapper {

    public static PrescriptionResponseDto toDto(PrescriptionEntity entity) {
        if (entity == null) return null;

        PrescriptionResponseDto dto = new PrescriptionResponseDto();
        dto.setPrescriptionId(entity.getId());
        dto.setAppointmentId(entity.getAppointmentEntity().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setValid(entity.getValid());
        dto.setIsCompleted(entity.getIsComplete());

        List<PrescriptionMedicineDto> medicineDtos =
                entity.getPrescriptionMedicineEntity()
                        .stream()
                        .map(PrescriptionMedicineMapper::toDto)
                        .collect(Collectors.toList());

        dto.setMedicineDtos(medicineDtos);

        return dto;
    }

}
