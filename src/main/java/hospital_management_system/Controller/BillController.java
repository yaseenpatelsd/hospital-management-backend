package hospital_management_sytem.Controller;

import hospital_management_sytem.Dto.Billing.BillingRequestDto;
import hospital_management_sytem.Dto.Billing.BillingResponseDto;
import hospital_management_sytem.Dto.Billing.MakePaymentDto;
import hospital_management_sytem.Service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bill")
public class BillController {
    private final BillingService billingService;

    public BillController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/staff/create")
    public ResponseEntity<BillingResponseDto> createBilling(Authentication authentication, @Valid @RequestBody BillingRequestDto dto){
        BillingResponseDto billingResponseDto=billingService.createBill(authentication,dto);
        return ResponseEntity.ok(billingResponseDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/reCreate/{billingId}")
    public ResponseEntity<BillingResponseDto> reCreate(Authentication authentication,@PathVariable Long billingId, @Valid @RequestBody BillingRequestDto dto){
        BillingResponseDto billingResponseDto=billingService.regenerateBill(authentication,billingId,dto);
        return ResponseEntity.ok(billingResponseDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/payment")
    public ResponseEntity<String> payment(Authentication authentication,@Valid @RequestBody MakePaymentDto paymentMode){
        billingService.makePayment(authentication,paymentMode);
        return ResponseEntity.ok("Payment happens Successfully");
    }
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/{billId}")
    public ResponseEntity<BillingResponseDto> getBill(Authentication authentication,@PathVariable Long billId){
        BillingResponseDto billingResponseDto=billingService.getBill(authentication,billId);
        return ResponseEntity.ok(billingResponseDto);
    }


}
