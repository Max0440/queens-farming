package edu.kit.informatik.game.board;

import java.util.HashMap;
import java.util.Map;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.type.PlantableTileType;
import edu.kit.informatik.game.type.VegetableType;

/**
 * Represents a board from a player in queens farming
 * 
 * @author uiljo
 * @version 1.0
 */
public class Board {

    private final Barn barn;
    private final Map<Location, PlantableTile> plantableTiles;

    /**
     * Instantiates a new {@link Board}
     */
    public Board() {
        this.barn = new Barn();
        this.plantableTiles = new HashMap<>();
        this.plantableTiles.put(new Location(-1, 0), new PlantableTile(PlantableTileType.GARDEN));
        this.plantableTiles.put(new Location(1, 0), new PlantableTile(PlantableTileType.GARDEN));
        this.plantableTiles.put(new Location(0, 1), new PlantableTile(PlantableTileType.FIELD));
    }

    /**
     * Returns the barn from the board.
     * 
     * @return the barn
     */
    public Barn getBarn() {
        return this.barn;
    }

    /**
     * Handles a turn in the game and returns a message indicating the results of
     * the turn.
     * 
     * @return a message indicating the results of the turn, or {@code null} if no
     *         vegetables have grown and no vegetables have spoiled.
     */
    public String startNextTurn() {
        StringBuilder sb = new StringBuilder();

        int totalGrown = 0;
        for (PlantableTile tile : this.plantableTiles.values()) {
            totalGrown += tile.grow();
        }

        if (totalGrown == 1) {
            sb.append("1 vegetable has grown since your last turn.");
        } else if (totalGrown > 1) {
            sb.append(String.format("%s vegetables have grown since your last turn.", totalGrown));
        }

        String spoiledVegetables = this.getBarn().startNextTurn();
        if (spoiledVegetables != null) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(spoiledVegetables);
        }

        if (sb.length() == 0) {
            return null;
        }

        return sb.toString();
    }

    private PlantableTile getPlantableTile(final int xCoordinate, final int yCoordinate) {
        return this.plantableTiles.get(new Location(xCoordinate, yCoordinate));
    }

    /**
     * Plants a given vegetable on the given tile.
     * 
     * @param xCoordinate The x-coordinate of the plantable tile to plant on.
     * @param yCoordinate The y-coordinate of the plantable tile to plant on.
     * @param vegetable   The vegetable to plant.
     * @throws GameException when tile doesn't exists or, vegetable is not plantable
     *                       or player doesn't own vegetable.
     */
    public void plant(final int xCoordinate, final int yCoordinate, final VegetableType vegetable)
            throws GameException {
        final PlantableTile tile = this.getPlantableTile(xCoordinate, yCoordinate);

        if (tile == null) {
            throw new GameException(ErrorMessages.LAND_NOT_OWNED);
        }

        tile.plant(vegetable);
    }

    /**
     * Harvests a vegetable from a given tile and returns the type of the vegetable
     * harvested.
     * 
     * @param xCoordinate     The x-coordinate of the plantable tile to harvest
     *                        from.
     * @param yCoordinate     The y-coordinate of the plantable tile to harvest
     *                        from.
     * @param amountToHarvest The amount of vegetables to harvest.
     * @return The type of vegetable harvested.
     * @throws GameException when the given tile doesn't exists or the vegetable is
     *                       not harvestable.
     */
    public VegetableType harvest(final int xCoordinate, final int yCoordinate, final int amountToHarvest)
            throws GameException {
        final PlantableTile tile = this.getPlantableTile(xCoordinate, yCoordinate);

        if (tile == null) {
            throw new GameException(ErrorMessages.LAND_NOT_OWNED);
        }

        return tile.harvest(amountToHarvest);
    }

    private boolean isOccupied(final int xCoordinate, final int yCoordinate) {
        if (xCoordinate == 0 && yCoordinate == 0) { // location of barn
            return true;
        }
        return this.getPlantableTile(xCoordinate, yCoordinate) != null;
    }

    private boolean hasNeighbor(final int xCoordinate, final int yCoordinate) {
        return this.isOccupied(xCoordinate, yCoordinate - 1)
                || this.isOccupied(xCoordinate - 1, yCoordinate)
                || this.isOccupied(xCoordinate + 1, yCoordinate);
    }

    /**
     * Checks if the given location is an placable space.
     * A location is considered placeable if it isn't occupied and there is a
     * neighbor to the left, to the right, or below it.
     * 
     * @param xCoordinate The x-coordinate of the location to check
     * @param yCoordinate The y-coordinate of the location to check
     * @return {@code true} if the location is placable, {@code false} if not
     */
    public boolean isPlacableSpace(final int xCoordinate, final int yCoordinate) {
        return !isOccupied(xCoordinate, yCoordinate) && hasNeighbor(xCoordinate, yCoordinate);
    }

    /**
     * Calculates the cost to buy land at a given location.
     * The price is calculated by the following formula:
     * price = 10 * (|xCoordinate| + |yCoordinate| - 1)
     * 
     * @param xCoordinate The x-coordinate of the land to calculate price from.
     * @param yCoordinate The y-coordinate of the land to calculate price from.
     * @return The pice to buy the land.
     */
    public int calculatePrice(final int xCoordinate, final int yCoordinate) {
        return 10 * ((Math.abs(xCoordinate) + Math.abs(yCoordinate)) - 1);
    }

    /**
     * Adds a new plantable tile at a given location the the board.
     * 
     * @param xCoordinate The x-coordinate of the land to add.
     * @param yCoordinate The y-coordinate of the land to add.
     * @param tileType    The type of plantable tile to add
     */
    public void addTile(final int xCoordinate, final int yCoordinate, final PlantableTileType tileType) {
        this.plantableTiles.put(new Location(xCoordinate, yCoordinate), new PlantableTile(tileType));
    }

    private int getMinXValue() {
        int currentIndex = 0;

        for (Location location : this.plantableTiles.keySet()) {
            if (location.getXCoordinate() < currentIndex) {
                currentIndex = location.getXCoordinate();
            }
        }

        return currentIndex;
    }

    private int getMaxXValue() {
        int currentIndex = 0;

        for (Location location : this.plantableTiles.keySet()) {
            if (location.getXCoordinate() > currentIndex) {
                currentIndex = location.getXCoordinate();
            }
        }

        return currentIndex;
    }

    private int getMaxYValue() {
        int currentIndex = 0;

        for (Location location : this.plantableTiles.keySet()) {
            if (location.getYCoordinate() > currentIndex) {
                currentIndex = location.getYCoordinate();
            }
        }

        return currentIndex;
    }

    private void fillIn(final char[][] board, final char[][] content, final int xOffset, final int yOffset) {
        for (int yCoordinate = 0; yCoordinate < content.length; yCoordinate++) {
            for (int xCoordinate = 0; xCoordinate < content[yCoordinate].length; xCoordinate++) {
                board[yCoordinate + yOffset][xCoordinate + xOffset] = content[yCoordinate][xCoordinate];
            }
        }
    }

    private int calculateXOffset(int index) {
        return (index - getMinXValue()) * 6;
    }

    private int calculateYOffset(int index) {
        return (getMaxYValue() - index) * 3;
    }

    private int getBoardWidth() {
        int widthInTiles = Math.abs(getMaxXValue() - getMinXValue()) + 1;
        return widthInTiles * 6 + 1;
    }

    private int getBoardHeight() {
        int heightInTiles = Math.abs(getMaxYValue()) + 1;
        return heightInTiles * 3;
    }

    @Override
    public String toString() {
        final int boardWidth = this.getBoardWidth();
        final int boardHeight = this.getBoardHeight();

        // create 2d char array that represents the board
        char[][] boardRepresentation = new char[boardHeight][boardWidth];
        for (int yCoordinate = 0; yCoordinate < boardHeight; yCoordinate++) {
            for (int xCoordinate = 0; xCoordinate < boardWidth; xCoordinate++) {
                boardRepresentation[yCoordinate][xCoordinate] = ' ';
            }
        }

        // fill in the barn
        final char[][] barnCharArray = this.barn.toCharArray();
        fillIn(boardRepresentation, barnCharArray, calculateXOffset(0), calculateYOffset(0));

        // fill in all plantable tiles
        for (final Map.Entry<Location, PlantableTile> entry : this.plantableTiles.entrySet()) {
            final Location currentLocation = entry.getKey();
            final PlantableTile currentTile = entry.getValue();

            final char[][] tileCharArray = currentTile.toCharArray();
            final int xOffset = calculateXOffset(currentLocation.getXCoordinate());
            final int yOffset = calculateYOffset(currentLocation.getYCoordinate());

            fillIn(boardRepresentation, tileCharArray, xOffset, yOffset);

        }

        // convert 2d char array to string
        StringBuilder sb = new StringBuilder();
        for (int rowIndex = 0; rowIndex < boardRepresentation.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < boardRepresentation[rowIndex].length; columnIndex++) {
                sb.append(boardRepresentation[rowIndex][columnIndex]);
            }

            if (rowIndex != boardRepresentation.length - 1) {
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
