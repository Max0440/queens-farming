package edu.kit.informatik.game;

/**
 * Represents an exception that occur in queens farming.
 * The implementation was inspired by the sample solution of task 5.
 * 
 * @author uiljo
 * @author Thomas Weber
 * @version 1.0
 */
public class GameException extends IllegalArgumentException {

    private static final long serialVersionUID = -97485091L;

    /**
     * Instantiates a new {@link GameException} with a given message.
     * 
     * @param message The error message of the exception.
     */
    public GameException(final String message) {
        super(message);
    }
}
