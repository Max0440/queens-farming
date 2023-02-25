package edu.kit.informatik.game.type;

import edu.kit.informatik.config.BoardConfig;
import edu.kit.informatik.config.GeneralConfig;

public enum PlantableTileType {

    GARDEN("Garden", BoardConfig.GARDEN, 2, GeneralConfig.GARDEN_PLANTABLE_VEGETABLES),
    FIELD("Field", BoardConfig.FIELD, 4, GeneralConfig.FIELD_PLANTABLE_VEGETABLES),
    LARGE_FIELD("Large Field", BoardConfig.LARGE_FIELD, 8, GeneralConfig.FIELD_PLANTABLE_VEGETABLES),
    FOREST("Forest", BoardConfig.FOREST, 4, GeneralConfig.FOREST_PLANTABLE_VEGETABLES),
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

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public String getBoardRepresentation() {
        return this.boardRepresentation;
    }

    public String getName() {
        return this.name;
    }

    public VegetableType[] getPlantableVegetables() {
        return this.plantableVegetables;
    }

    public boolean isPlantableVegetable(final VegetableType vegetable) {
        for (final VegetableType plantableVegetable : this.plantableVegetables) {
            if (plantableVegetable.equals(vegetable)) {
                return true;
            }
        }

        return false;
    }
}