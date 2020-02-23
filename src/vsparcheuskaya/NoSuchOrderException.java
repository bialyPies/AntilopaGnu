package vsparcheuskaya;

public class NoSuchOrderException extends Exception {
    private String message;
    public NoSuchOrderException(String mes){
        this.message = mes;
    }
    public String getMessage(){return message;};
}
