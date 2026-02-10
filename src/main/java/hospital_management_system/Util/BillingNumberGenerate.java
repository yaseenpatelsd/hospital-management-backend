package hospital_management_sytem.Util;

import org.springframework.stereotype.Component;

@Component
public class BillingNumberGenerate {

    public static String generateBill() {
        return "INV-" + System.currentTimeMillis();
    }
}
