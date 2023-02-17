package edu.kit.informatik.tiles;

import edu.kit.informatik.Countdown;
import edu.kit.informatik.VegetableType;

public class PlantableTile extends Tile {

    private final PlantableTileType tileType;
    private VegetableType plantedVegetable;
    private int plantedVegetableCount;
    private Countdown growCountdown;

    // TODO richtig registrieren ob etwas wächst
    public PlantableTile(final int xCoordinate, final int yCoordinate, final PlantableTileType tileType) {
        super(xCoordinate, yCoordinate);

        this.growCountdown = new Countdown();
        this.tileType = tileType;
        this.plantedVegetableCount = 0;
    }

    @Override
    public int grow() {
        if (!this.growCountdown.isActive()) {
            return 0;
        }

        if (!this.growCountdown.nextStep(-1)) {
            return 0;
        }
        this.growCountdown.setValue(this.plantedVegetable.getTimeToGrow());

        int newVegetableCount = this.plantedVegetableCount * 2;
        if (newVegetableCount >= this.tileType.getMaxCapacity()) {
            newVegetableCount = this.tileType.getMaxCapacity();
            this.growCountdown.setActive(false);
        }

        final int newlyGrownVegetable = newVegetableCount- this.plantedVegetableCount ;
        this.plantedVegetableCount = newVegetableCount;
        return newlyGrownVegetable;
    }

    public void plant(final VegetableType vegetable) {
        if (this.plantedVegetableCount > 0) {
            // TODO Error
            System.out.println("ERROR FIELD NOT EMPTY");
            return;
        }

        if (!this.tileType.isPlantableVegetable(vegetable)) {
            // TODO Error
            System.out.println("ERROR NOT PLANTABLE");
            return;
        }

        this.plantedVegetable = vegetable;
        this.plantedVegetableCount = 1;

        this.growCountdown.setValue(vegetable.getTimeToGrow());
        this.growCountdown.setMinValue(0);
        this.growCountdown.setActive(true);
    }

    // TODO deactivate countdown
    public VegetableType harvest(final int amountToHarvest) {
        if (this.plantedVegetableCount < amountToHarvest) {
            // TODO Error
            return null;
        }

        this.plantedVegetableCount -= amountToHarvest;

        return plantedVegetable;
    }

    @Override
    public char[][] toCharArray() {
        char[][] c = new char[3][7];

        if (this.growCountdown.isActive()) {
            c[0] = String.format("| %s %s |", this.tileType.getAbbreviation(), this.growCountdown.getValue())
                    .toCharArray();
        } else {
            c[0] = String.format("| %s * |", this.tileType.getAbbreviation()).toCharArray();
        }

        if (this.plantedVegetableCount > 0) {
            c[1] = String.format("|  %s  |", this.plantedVegetable.getAbbreviation()).toCharArray();
        } else {
            c[1] = "|     |".toCharArray();
        }

        c[2] = String.format("| %d/%d |", this.plantedVegetableCount, this.tileType.getMaxCapacity()).toCharArray();
        return c;
    }
}
