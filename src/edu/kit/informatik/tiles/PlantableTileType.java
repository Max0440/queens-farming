package edu.kit.informatik.tiles;

import edu.kit.informatik.Config;
import edu.kit.informatik.VegetableType;

public enum PlantableTileType {

    GARDEN(2, "G", "Garden", Config.GARDEN_PLANTABLE_VEGETABLES),
    FIELD(4, "Fi", "Field", Config.FIELD_PLANTABLE_VEGETABLES),
    LARGE_FIELD(8, "LFi", "Large Field", Config.FIELD_PLANTABLE_VEGETABLES),
    FOREST(4, "Fo", "Forest", Config.FOREST_PLANTABLE_VEGETABLES),
    LARGE_FOREST(8, "LFo", "Large Forest", Config.FOREST_PLANTABLE_VEGETABLES);

    private int maxCapacity;
    private String abbreviation;
    private String name;
    private VegetableType[] plantableVegetables;

    private PlantableTileType(int maxCapacity, String abbreviation, String name, VegetableType[] plantableVegetables) {
        this.maxCapacity = maxCapacity;
        this.abbreviation = abbreviation;
        this.name = name;
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

    public boolean isPlantableVegetable(VegetableType vegetable) {
        for (VegetableType plantableVegetable : this.plantableVegetables) {
            if (plantableVegetable.equals(vegetable)) {
                return true;
            }
        }

        return false;
    }
}
