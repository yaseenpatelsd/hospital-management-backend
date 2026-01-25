package hospital_management_sytem.Exeption;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super(message);
    }
}
