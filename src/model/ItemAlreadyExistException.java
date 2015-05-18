package model;

public class ItemAlreadyExistException extends  Exception {
    public ItemAlreadyExistException() {
        super();
    }
    public ItemAlreadyExistException(String message) {
        super(message);
    }
}
