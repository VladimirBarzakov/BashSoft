package main.bg.softuni.exceptions;

public class InvalidInputException extends RuntimeException {
    private static final String INVALID_INPUT_MESSAGE = "Invalid input - %s";

    public InvalidInputException(String message) {
        super(String.format(INVALID_INPUT_MESSAGE, message));
    }
}
