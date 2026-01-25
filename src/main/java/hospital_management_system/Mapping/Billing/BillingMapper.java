package hospital_management_sytem.Mapping.Billing;

import hospital_management_sytem.Dto.Billing.BillingMedicineResponseDto;
import hospital_management_sytem.Dto.Billing.BillingResponseDto;
import hospital_management_sytem.Entity.Billing.BillingEntity;
import java.util.List;
import java.util.stream.Collectors;


public class BillingMapper {

    public static BillingResponseDto toDto(BillingEntity entity){
        if (entity==null)return null;

        BillingResponseDto dto=new BillingResponseDto();
        dto.setAppointmentId(entity.getAppointment().getId());
        dto.setInvoiceNumber(entity.getInvoiceNumber());


        List<BillingMedicineResponseDto> medicine =
                entity.getBillingMedicine()
                        .stream()
                        .map(BillingMedicineMapper::toDto)
                        .collect(Collectors.toList());

        dto.setMedicine(medicine);

        return dto;
    }
}
