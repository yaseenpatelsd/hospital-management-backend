package hospital_management_sytem.Service;

import hospital_management_sytem.Entity.UserEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void AccountVerification(UserEntity user, String otp){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Verify Your Account – One-Time OTP");
        simpleMailMessage.setText("Dear " + user.getUsername()+",\n" +
                "\n" +
                "Thank you for registering with YM Hospital.\n" +
                "\n" +
                "To complete your account verification, please use the One-Time Password OTP provided below:\n" +
                "\n" +
                "\uD83D\uDD10 **Your Verification Code**\n" +
                otp+
                "\n" +
                "\n" +
                "⏳ This OTP is valid for 10 minutes.\n" +
                "For your security, please do not share this code with anyone.\n" +
                "\n" +
                "If you did not create an account with us, please ignore this email. No further action is required.\n" +
                "\n" +
                "Should you need assistance, feel free to contact our support team.\n" +
                "\n" +
                "Warm regards,  \n" +
                "**Hospital Management System Team**  \n" +
                "\uD83D\uDCE7 YMHospital@hospital.com  \n" +
                "\uD83C\uDF10 www.YMHospital.com\n");


        javaMailSender.send(simpleMailMessage);
    }
    public void forPasswordReset(UserEntity user, String otp) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("OTP for Password Change Request-ys Hospital");
        simpleMailMessage.setText("Dear " + user.getUsername() + "\n" +

                "We received a request to change the password for your YM HOSPITAL account." + "\n" +

                "Please use the OTP below to continue:" + "\n" +

                "OTP:" + "\n" + otp + "\n" +

                "This OTP is valid for 10 minutes." + "\n" +
                "Do not share this code with anyone for security reasons." + "\n" +
                "If you did not request this password change, please ignore this email or contact our support team immediately." + "\n" +
                "Thank you for trusting {{Hospital Name}} with your healthcare information." + "\n" +

                " Regards," + "\n" +
                "YS Hospital" + "\n" +
                "Support: " + "YMManagement@gmail.com"
        );
          javaMailSender.send(simpleMailMessage);
    }
}
