package edu.kit.informatik.tiles;

import edu.kit.informatik.VegetableType;
import edu.kit.informatik.config.GeneralConfig;

public enum PlantableTileType {

    GARDEN("Garden", "G", 2, GeneralConfig.GARDEN_PLANTABLE_VEGETABLES),
    FIELD("Field", "Fi", 4, GeneralConfig.FIELD_PLANTABLE_VEGETABLES),
    LARGE_FIELD("Large Field", "LFi", 8, GeneralConfig.FIELD_PLANTABLE_VEGETABLES),
    FOREST("Forest", "Fo", 4, GeneralConfig.FOREST_PLANTABLE_VEGETABLES),
    LARGE_FOREST("Large Forest", "LFo", 8, GeneralConfig.FOREST_PLANTABLE_VEGETABLES);

    private final int maxCapacity;
    private final String abbreviation;
    private final String name;
    private final VegetableType[] plantableVegetables;

    private PlantableTileType(final String name, final String abbreviation, final int maxCapacity, final VegetableType[] plantableVegetables) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.abbreviation = abbreviation;
        this.plantableVegetables = plantableVegetables;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public String getAbbreviation() {
        return this.abbreviation;
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
