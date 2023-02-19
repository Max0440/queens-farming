package edu.kit.informatik.game;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.tiles.Barn;
import edu.kit.informatik.game.tiles.PlantableTile;
import edu.kit.informatik.game.tiles.PlantableTileType;

public class Board {

    private final Barn barn;
    private final List<PlantableTile> plantableTiles;

    public Board() {
        // TODO as map
        this.barn = new Barn();
        this.plantableTiles = new ArrayList<>();
        this.plantableTiles.add(new PlantableTile(-1, 0, PlantableTileType.GARDEN));
        this.plantableTiles.add(new PlantableTile(1, 0, PlantableTileType.GARDEN));
        this.plantableTiles.add(new PlantableTile(0, 1, PlantableTileType.FIELD));
    }

    public Barn getBarn() {
        return this.barn;
    }

    public String startNextTurn() {
        StringBuilder sb = new StringBuilder();

        // TODO var names & nicer
        int totalGrown = 0;
        for (int i = 0; i < this.plantableTiles.size(); i++) {
            totalGrown += this.plantableTiles.get(i).grow();
        }

        if (totalGrown == 1) {
            sb.append("1 vegetable has grown since your last turn.");
        } else if (totalGrown > 1) {
            sb.append(String.format("%s vegetables have grown since your last turn.", totalGrown));
        }

        String barn = this.getBarn().startNextTurn();
        if (barn != null) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(barn);
        }

        if (sb.length() == 0) {
            return null;
        }

        return sb.toString();
    }

    private PlantableTile getPlantableTile(final int xCoordinate, final int yCoordinate) {
        for (int i = 0; i < this.plantableTiles.size(); i++) {
            if (this.plantableTiles.get(i).getXCoordinate() == xCoordinate
                    && this.plantableTiles.get(i).getYCoordinate() == yCoordinate) {
                return this.plantableTiles.get(i);
            }
        }
        return null;
    }

    public void plant(final int xCoordinate, final int yCoordinate, final VegetableType vegetable)
            throws GameException {
        final PlantableTile tile = this.getPlantableTile(xCoordinate, yCoordinate);

        if (tile == null) {
            throw new GameException(ErrorMessages.LAND_NOT_OWNED);
        }

        tile.plant(vegetable);
    }

    public VegetableType harvest(final int xCoordinate, final int yCoordinate, final int amountToHarvest)
            throws GameException {
        final PlantableTile tile = this.getPlantableTile(xCoordinate, yCoordinate);

        if (tile == null) {
            throw new GameException(ErrorMessages.LAND_NOT_OWNED);
        }

        return tile.harvest(amountToHarvest);
    }

    private boolean isOccupied(final int xCoordinate, final int yCoordinate) {
        if (xCoordinate == 0 && yCoordinate == 0) {
            return true;
        }
        return this.getPlantableTile(xCoordinate, yCoordinate) != null;
    }

    private boolean hasNeighbor(final int xCoordinate, final int yCoordinate) {
        return this.isOccupied(xCoordinate, yCoordinate - 1)
                || this.isOccupied(xCoordinate - 1, yCoordinate)
                || this.isOccupied(xCoordinate + 1, yCoordinate);
    }

    public boolean isPlacableSpace(final int xCoordinate, final int yCoordinate) {
        return !isOccupied(xCoordinate, yCoordinate) && hasNeighbor(xCoordinate, yCoordinate);
    }

    public int calculatePrice(final int xCoordinate, final int yCoordinate) {
        return 10 * ((Math.abs(xCoordinate) + Math.abs(yCoordinate)) - 1);
    }

    public void addTile(final int xCoordinate, final int yCoordinate, final PlantableTileType tileType) {
        final PlantableTile newTile = new PlantableTile(xCoordinate, yCoordinate, tileType);
        this.plantableTiles.add(newTile);
    }

    private int getMinXValue() {
        int currentIndex = 0;
        for (int i = 0; i < this.plantableTiles.size(); i++) {
            if (this.plantableTiles.get(i).getXCoordinate() < currentIndex) {
                currentIndex = this.plantableTiles.get(i).getXCoordinate();
            }
        }
        return currentIndex;
    }

    private int getMaxXValue() {
        int currentIndex = 0;
        for (int i = 0; i < this.plantableTiles.size(); i++) {
            if (this.plantableTiles.get(i).getXCoordinate() > currentIndex) {
                currentIndex = this.plantableTiles.get(i).getXCoordinate();
            }
        }
        return currentIndex;
    }

    private int getMaxYValue() {
        int currentIndex = 0;
        for (int i = 0; i < this.plantableTiles.size(); i++) {
            if (this.plantableTiles.get(i).getYCoordinate() > currentIndex) {
                currentIndex = this.plantableTiles.get(i).getYCoordinate();
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
        for (int i = 0; i < this.plantableTiles.size(); i++) {
            final PlantableTile currentTile = this.plantableTiles.get(i);
            final char[][] tileCharArray = currentTile.toCharArray();
            final int xOffset = calculateXOffset(this.plantableTiles.get(i).getXCoordinate());
            final int yOffset = calculateYOffset(this.plantableTiles.get(i).getYCoordinate());

            fillIn(boardRepresentation, tileCharArray, xOffset, yOffset);
        }

        // convert 2d char array to string
        // TODO last line break?
        // TODO Rename variables
        StringBuilder sb = new StringBuilder();
        for (char[] cs : boardRepresentation) {
            for (char cs2 : cs) {
                sb.append(cs2);
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
