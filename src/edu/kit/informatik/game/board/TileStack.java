package edu.kit.informatik.game.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.kit.informatik.game.type.PlantableTileType;

/**
 * Represents a stack of plantable tiles in queens farming. The stack contains a
 * number of different tiles determined by the amount of players in the game,
 * which can be drawn randomly.
 * 
 * @author uiljo
 * @version 1.0
 */
public class TileStack {
    private final List<PlantableTileType> stack;

    /**
     * Instantiates a new {@link TileStack}.
     * 
     * @param playerCount The number of players in the game.
     * @param seed        The random seed for shuffling tile stack.
     */
    public TileStack(final int playerCount, final long seed) {
        stack = new ArrayList<>();

        // add 2n garden tiles
        for (int i = 0; i < 2 * playerCount; i++) {
            this.stack.add(PlantableTileType.GARDEN);
        }

        // add 3n field tiles
        for (int i = 0; i < 3 * playerCount; i++) {
            this.stack.add(PlantableTileType.FIELD);
        }

        // add 2n large field tiles
        for (int i = 0; i < 2 * playerCount; i++) {
            this.stack.add(PlantableTileType.LARGE_FIELD);
        }

        // add 2n forest tiles
        for (int i = 0; i < 2 * playerCount; i++) {
            this.stack.add(PlantableTileType.FOREST);
        }

        // add n large forest tiles
        for (int i = 0; i < playerCount; i++) {
            this.stack.add(PlantableTileType.LARGE_FOREST);
        }

        // shuffle tiles
        Collections.shuffle(this.stack, new Random(seed));
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
     * Checks if the stack is empty.
     * 
     * @return {@code true} if the stack is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * Draws a random tile from the tile stack and removes it.
     * 
     * @return the tile type of the drawn tile.
     */
    public PlantableTileType drawTile() {
        final PlantableTileType tile = this.stack.get(0);
        this.stack.remove(0);

        return tile;
    }
}