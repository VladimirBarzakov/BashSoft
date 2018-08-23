package main.bg.softuni.exceptions;

public class InvalidCommandException extends RuntimeException {

    private static final String COMMAND_EXCEPTION_MESSAGE = "The command '%s' is invalid";

    public InvalidCommandException(String command) {
        super(String.format(COMMAND_EXCEPTION_MESSAGE, command));
    }
}
