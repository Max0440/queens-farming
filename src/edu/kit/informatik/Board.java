package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.tiles.Barn;
import edu.kit.informatik.tiles.PlantableTile;
import edu.kit.informatik.tiles.PlantableTileType;
import edu.kit.informatik.tiles.Tile;

public class Board {

    private final List<Tile> tiles;

    public Board() {
        // TODO as map
        this.tiles = new ArrayList<>();
        this.tiles.add(new Barn());
        this.tiles.add(new PlantableTile(-1, 0, PlantableTileType.GARDEN));
        this.tiles.add(new PlantableTile(1, 0, PlantableTileType.GARDEN));
        this.tiles.add(new PlantableTile(0, 1, PlantableTileType.FIELD));
    }

    public Barn getBarn() {
        // TODO nicer
        return (Barn) this.tiles.get(0);
    }

    private Tile getTile(final int xCoordinate, final int yCoordinate) {
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).getXCoordinate() == xCoordinate
                    && this.tiles.get(i).getYCoordinate() == yCoordinate) {
                return this.tiles.get(i);
            }
        }
        return null;
    }

    public String startNextTurn() {
        // TODO var names & nicer
        int totalGrown = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            // if (this.tiles.get(i) instanceof Barn) {
            // continue;
            // }
            // this.tiles.get(i).startNextTurn();
            totalGrown += this.tiles.get(i).grow();
        }
        String s = "";
        if (totalGrown == 1) {
            s = "1 vegetable has grown since your last turn.";
        } else if (totalGrown > 1) {
            s = String.format("%s vegetables have grown since your last turn. ", totalGrown);
        }

        String asd = this.getBarn().startNextTurn();
        if (asd != null) {
            return s + System.lineSeparator() + asd;
        }
        return s;
    }

    public String plant(final int xCoordinate, final int yCoordinate, final VegetableType vegetable)
            throws GameException {
        // TODO type cast
        final PlantableTile tile = (PlantableTile) this.getTile(xCoordinate, yCoordinate);

        if (tile == null) {
            // Error tile not found
            throw new GameException(ErrorMessages.LAND_NOT_OWNED);
        }

        tile.plant(vegetable);

        return null;
    }

    private boolean isOccupied(final int xCoordinate, final int yCoordinate) {
        return this.getTile(xCoordinate, yCoordinate) != null;
    }

    private boolean hasNeighborBelow(final int xCoordinate, final int yCoordinate) {
        return this.isOccupied(xCoordinate, yCoordinate - 1);
    }

    private boolean hasNeighborRight(final int xCoordinate, final int yCoordinate) {
        return this.isOccupied(xCoordinate + 1, yCoordinate);
    }

    private boolean hasNeighborLeft(final int xCoordinate, final int yCoordinate) {
        return this.isOccupied(xCoordinate - 1, yCoordinate);
    }

    private boolean hasNeighbor(final int xCoordinate, final int yCoordinate) {
        return hasNeighborBelow(xCoordinate, yCoordinate) || hasNeighborLeft(xCoordinate, yCoordinate)
                || hasNeighborRight(xCoordinate, yCoordinate);
    }

    public boolean isPlacableSpace(final int xCoordinate, final int yCoordinate) {
        return !isOccupied(xCoordinate, yCoordinate) && hasNeighbor(xCoordinate, yCoordinate);
    }

    public int calculatePrice(final int xCoordinate, final int yCoordinate) {
        return 10 * ((Math.abs(xCoordinate) + Math.abs(yCoordinate)) - 1);
    }

    public void addTile(final int xCoordinate, final int yCoordinate, final PlantableTileType tileType) {
        final Tile newTile = new PlantableTile(xCoordinate, yCoordinate, tileType);
        this.tiles.add(newTile);
    }

    private int getMinXValue() {
        int currentIndex = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).getXCoordinate() < currentIndex) {
                currentIndex = this.tiles.get(i).getXCoordinate();
            }
        }
        return currentIndex;
    }

    private int getMinYValue() {
        // TODO Is always 0
        int currentIndex = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).getYCoordinate() < currentIndex) {
                currentIndex = this.tiles.get(i).getYCoordinate();
            }
        }
        return currentIndex;
    }

    private int getMaxXValue() {
        int currentIndex = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).getXCoordinate() > currentIndex) {
                currentIndex = this.tiles.get(i).getXCoordinate();
            }
        }
        return currentIndex;
    }

    private int getMaxYValue() {
        int currentIndex = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).getYCoordinate() > currentIndex) {
                currentIndex = this.tiles.get(i).getYCoordinate();
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
        int heightInTiles = Math.abs(getMaxYValue() - getMinYValue()) + 1;
        return heightInTiles * 3;
    }

    @Override
    public String toString() {
        // TODO Rename variables
        // TODO remove last line
        final int boardWidth = this.getBoardWidth();
        final int boardHeight = this.getBoardHeight();

        char[][] boardRepresentation = new char[boardHeight][boardWidth];
        for (int yCoordinate = 0; yCoordinate < boardHeight; yCoordinate++) {
            for (int xCoordinate = 0; xCoordinate < boardWidth; xCoordinate++) {
                boardRepresentation[yCoordinate][xCoordinate] = ' ';
            }
        }

        for (int i = 0; i < this.tiles.size(); i++) {
            char[][] c = this.tiles.get(i).toCharArray();
            fillIn(boardRepresentation, c, calculateXOffset(this.tiles.get(i).getXCoordinate()),
                    calculateYOffset(this.tiles.get(i).getYCoordinate()));
        }

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
