package hospital_management_sytem.Exeption;

public class ResourceNotAvailable extends RuntimeException{
    public ResourceNotAvailable(String message){
        super(message);
    }
}
