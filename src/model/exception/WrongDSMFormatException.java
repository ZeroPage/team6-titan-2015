package model.exception;

public class WrongDSMFormatException extends Exception {
    public WrongDSMFormatException(){
        super("Wrong DSM format");
    }
}
