package hospital_management_sytem.Mapping.Billing;

import hospital_management_sytem.Dto.Billing.BillingMedicineResponseDto;
import hospital_management_sytem.Entity.Billing.BillingMedicineEntity;

public class BillingMedicineMapper {

    public static BillingMedicineResponseDto toDto(BillingMedicineEntity entity){
        if (entity==null)return null;

        BillingMedicineResponseDto dto=new BillingMedicineResponseDto();
        dto.setMedicineCode(entity.getMedicineCode());
        dto.setMedicineName(entity.getMedicineName());
        dto.setBrandName(entity.getBrandName());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());

        return dto;

    }
}
