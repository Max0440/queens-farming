package edu.kit.informatik.game.board;

import java.util.Objects;

/**
 * Represents a location of a {@link PlantableTile} on the {@link Board}.
 * 
 * @author uiljo
 * @version 1.0
 */
public class Location {

    private final int xCoordinate;
    private final int yCoordinate;

    /**
     * Instantiates a new {@link Location}.
     * 
     * @param xCoordinate The x-coordinate of location.
     * @param yCoordinate The y-coordinate of location.
     */
    public Location(final int xCoordinate, final int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Returns the x-coordinate of the location.
     * 
     * @return x coordinate.
     */
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    /**
     * Returns the y-coordinate of the location.
     * 
     * @return y coordinate.
     */
    public int getYCoordinate() {
        return this.yCoordinate;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || !this.getClass().equals(object.getClass())) {
            return false;
        }

        final Location locationToCompare = (Location) object;
        return this.xCoordinate == locationToCompare.getXCoordinate()
                && this.yCoordinate == locationToCompare.getYCoordinate();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
}
