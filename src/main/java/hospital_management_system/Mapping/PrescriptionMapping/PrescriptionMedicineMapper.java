package hospital_management_sytem.Mapping.PrescriptionMapping;

import hospital_management_sytem.Dto.PrescriptionDto.PrescriptionMedicineDto;

import hospital_management_sytem.Entity.MedicineEntity;
import hospital_management_sytem.Entity.Prescription.PrescriptionMedicineEntity;

public class PrescriptionMedicineMapper {

    public static PrescriptionMedicineDto toDto(PrescriptionMedicineEntity entity){
        if (entity==null)return null;


        MedicineEntity medicine=entity.getMedicineEntity();
        PrescriptionMedicineDto dto = new PrescriptionMedicineDto();
        dto.setMedicineCode(medicine.getMedicineCode());
        dto.setName(medicine.getName());
        dto.setBrandName(medicine.getBrandName());
        dto.setCategory(medicine.getCategory());
        dto.setStrength(medicine.getStrength());

        dto.setQuantity(entity.getQuantity());
        dto.setDosage(entity.getDosage());
        dto.setFrequency(entity.getFrequency());
        dto.setDuration(entity.getDuration());

        return dto;
    }
}
