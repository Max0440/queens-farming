package edu.kit.informatik.game.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.kit.informatik.type.PlantableTileType;

public class TileStack {
    private final List<PlantableTileType> stack;

    /**
     * Instantiates a new {@link TileStack}
     * 
     * @param playerCount number of players in the game
     * @param seed        seed for shuffling tile stack
     */
    public TileStack(int playerCount, long seed) {
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
     * Returns if the tile stack is empty
     * 
     * @return if the tile stack is empty
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * Returns the first tile from the stack and deletes it
     * 
     * @return first tile from stack
     */
    public PlantableTileType drawTile() {
        final PlantableTileType tile = this.stack.get(0);
        this.stack.remove(0);

        return tile;
    }
}
