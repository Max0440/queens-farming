package edu.kit.informatik.game.board;

/**
 * Constructs a string representation of the board.
 * Each tile can be added to the board by a char array.
 */
public class BoardStringBuilder {
    private final char[][] boardRepresentation;
    private final int maxXValue;
    private final int minXValue;
    private final int maxYValue;

    public BoardStringBuilder(int maxXValue, int minXValue, int maxYValue) {
        this.maxXValue = maxXValue;
        this.minXValue = minXValue;
        this.maxYValue = maxYValue;

        int boardHeight = calculateBoardHeight();
        int boardWidth = calculateBoardWidth();

        this.boardRepresentation = new char[boardHeight][boardWidth];

        for (int yCoordinate = 0; yCoordinate < boardHeight; yCoordinate++) {
            for (int xCoordinate = 0; xCoordinate < boardWidth; xCoordinate++) {
                this.boardRepresentation[yCoordinate][xCoordinate] = ' ';
            }
        }
    }

    private int calculateBoardWidth() {
        int widthInTiles = Math.abs(this.maxXValue - this.minXValue) + 1;
        return widthInTiles * 6 + 1;
    }

    private int calculateBoardHeight() {
        int heightInTiles = Math.abs(this.maxYValue) + 1;
        return heightInTiles * 3;
    }

    private int calculateXOffset(int index) {
        return (index - this.minXValue) * 6;
    }

    private int calculateYOffset(int index) {
        return (this.maxYValue - index) * 3;
    }

    /**
     * Fills in a part of the board with the content from the given 2d-array of
     * characters.
     * 
     * @param content  The content of the tile.
     * @param location The location of the tile.
     */
    public void fillIn(final char[][] content, Location location) {
        final int xOffset = calculateXOffset(location.getXCoordinate());
        final int yOffset = calculateYOffset(location.getYCoordinate());

        for (int yCoordinate = 0; yCoordinate < content.length; yCoordinate++) {
            for (int xCoordinate = 0; xCoordinate < content[yCoordinate].length; xCoordinate++) {
                this.boardRepresentation[yCoordinate + yOffset][xCoordinate
                        + xOffset] = content[yCoordinate][xCoordinate];
            }
        }
    }

    /**
     * Returns the string representation of the board builder.
     * 
     * @return board string representation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int rowIndex = 0; rowIndex < this.boardRepresentation.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < this.boardRepresentation[rowIndex].length; columnIndex++) {
                sb.append(this.boardRepresentation[rowIndex][columnIndex]);
            }

            if (rowIndex != this.boardRepresentation.length - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
