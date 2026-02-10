package hospital_management_sytem.Mapping;

import hospital_management_sytem.Dto.MedicineDto.MedicineRequestDto;
import hospital_management_sytem.Dto.MedicineDto.MedicineResponseDto;
import hospital_management_sytem.Entity.MedicineEntity;

public class MedicineMapper {

    public static MedicineEntity toEntity(MedicineRequestDto dto){
        if (dto==null)return null;

        MedicineEntity entity=new MedicineEntity();
        entity.setMedicineCode(dto.getMedicineCode());
        entity.setName(dto.getName());
        entity.setBrandName(dto.getBrandName());
        entity.setCategory(dto.getCategory());
        entity.setStrength(dto.getStrength());
        entity.setPrice(dto.getPrice());
        entity.setStockQuantity(dto.getStockQuantity());
        entity.setExpiryDate(dto.getExpiryDate());

        return entity;
    }

    public static MedicineRequestDto toRequestDto(MedicineEntity entity){
        if (entity==null)return null;

        MedicineRequestDto dto=new MedicineRequestDto();
        dto.setMedicineCode(entity.getMedicineCode());
        dto.setName(entity.getName());
        dto.setBrandName(entity.getBrandName());
        dto.setCategory(entity.getCategory());
        dto.setStrength(entity.getStrength());
        dto.setPrice(entity.getPrice());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setExpiryDate(entity.getExpiryDate());

        return dto;
    }

    public static MedicineResponseDto toResponseDto(MedicineEntity entity){
        if (entity==null)return null;

        MedicineResponseDto dto=new MedicineResponseDto();
        dto.setId(entity.getId());
        dto.setMedicineCode(entity.getMedicineCode());
        dto.setName(entity.getName());
        dto.setBrandName(entity.getBrandName());
        dto.setCategory(entity.getCategory());
        dto.setStrength(entity.getStrength());
        dto.setPrice(entity.getPrice());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setExpiryDate(entity.getExpiryDate());

        return dto;
    }
}
