package hospital_management_sytem.Exeption;

public class ResourceAlreadyAvailable extends RuntimeException{
    public ResourceAlreadyAvailable(String message){
        super(message);
    }
}
