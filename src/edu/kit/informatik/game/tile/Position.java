package edu.kit.informatik.game.tile;

import java.util.Objects;

public class Position {

    private final int xCoordinate;
    private final int yCoordinate;

    public Position(final int xCoordinate, final int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return this.xCoordinate;
    }

    public int getYCoordinate() {
        return this.yCoordinate;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !this.getClass().equals(object.getClass())) {
            return false;
        }

        Position positionToCompare = (Position) object;
        return this.xCoordinate == positionToCompare.getXCoordinate()
                && this.yCoordinate == positionToCompare.getYCoordinate();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
}
