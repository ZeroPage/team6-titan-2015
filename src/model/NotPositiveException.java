package model;

public class NotPositiveException extends  Exception{
    public NotPositiveException() {
        super("Please input Positive");
    }
}
