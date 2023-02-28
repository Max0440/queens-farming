package edu.kit.informatik.game.type;

import edu.kit.informatik.config.BoardConfig;
import edu.kit.informatik.config.GeneralConfig;

/**
 * Enum of the types of plantable tiles in the game
 */
public enum PlantableTileType {
    // TODO check with required checkstyle
    /**
     * Garden tile type
     */
    GARDEN("Garden", BoardConfig.GARDEN, 2, GeneralConfig.GARDEN_PLANTABLE_VEGETABLES),

    /**
     * Field tile type
     */
    FIELD("Field", BoardConfig.FIELD, 4, GeneralConfig.FIELD_PLANTABLE_VEGETABLES),

    /**
     * Large field tile type
     */
    LARGE_FIELD("Large Field", BoardConfig.LARGE_FIELD, 8, GeneralConfig.FIELD_PLANTABLE_VEGETABLES),

    /**
     * Forest tile type
     */
    FOREST("Forest", BoardConfig.FOREST, 4, GeneralConfig.FOREST_PLANTABLE_VEGETABLES),

    /**
     * Large forest tile type
     */
    LARGE_FOREST("Large Forest", BoardConfig.LARGE_FOREST, 8, GeneralConfig.FOREST_PLANTABLE_VEGETABLES);

    private final int maxCapacity;
    private final String boardRepresentation;
    private final String name;
    private final VegetableType[] plantableVegetables;

    private PlantableTileType(final String name, final String boardRepresentation, final int maxCapacity,
            final VegetableType[] plantableVegetables) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.boardRepresentation = boardRepresentation;
        this.plantableVegetables = plantableVegetables;
    }

    /**
     * Returns the maximum capacity for vegetables of the tile
     * 
     * @return max capacity of the tile
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    /**
     * Returns the representation of the tile
     * 
     * @return the representation of the tile
     */
    public String getBoardRepresentation() {
        return this.boardRepresentation;
    }

    /**
     * Returns the name of the tile.
     * 
     * @return name of tile.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns an array of all plantable vegetable types on the tile.
     * 
     * @return The plantable vegetables.
     */
    public VegetableType[] getPlantableVegetables() {
        return this.plantableVegetables;
    }

    /**
     * Checks if a given vegetable is plantable on the tile.
     * 
     * @param vegetable Type of vegetable to check.
     * @return {@code true} if the vegetable is plantable {@code false} otherwise.
     */
    public boolean isPlantableVegetable(final VegetableType vegetable) {
        for (final VegetableType plantableVegetable : this.plantableVegetables) {
            if (plantableVegetable.equals(vegetable)) {
                return true;
            }
        }

        return false;
    }
}