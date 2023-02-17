package edu.kit.informatik.tiles;

public class Tile {

    private final int xCoordinate;
    private final int yCoordinate;

    public Tile(final int xCoordinate, final int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return this.xCoordinate;
    }

    public int getYCoordinate() {
        return this.yCoordinate;
    }

    public char[][] toCharArray() {
        return new char[0][0];
    }

    public String startNextTurn() {
        return null;
    }

    public int grow() {
        return 0;
    }
}
