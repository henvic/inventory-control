package Exceptions;

public class InvalidInputException extends Exception {
    public InvalidInputException() {
    }

    public InvalidInputException(String field) {
        super("field: " + field);
    }
}
