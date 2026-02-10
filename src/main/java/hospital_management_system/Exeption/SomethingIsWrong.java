package hospital_management_sytem.Exeption;

public class SomethingIsWrong extends RuntimeException{
    public SomethingIsWrong(String message){
        super(message);
    }
}
