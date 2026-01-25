package hospital_management_sytem.Exeption;

public class InvalidDetails extends RuntimeException{
    public InvalidDetails(String  message){
        super(message);
    }
}
