package hospital_management_sytem.Util;

import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {


    public String otpGeneration(){
        int otp=100000+(int)(Math.random()*900000);
        return String.valueOf(otp);
    }

    public long otpExpire(long minute){
        return System.currentTimeMillis()+(minute*60*1000);
    }
}
