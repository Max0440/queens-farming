package edu.kit.informatik.game;

// TODO Plagiat kennzeichen
public class GameException extends IllegalArgumentException {
    private static final long serialVersionUID = -97485091L;

    public GameException() {
        super();
    }

    public GameException(final String message) {
        super(message);
    }
}
