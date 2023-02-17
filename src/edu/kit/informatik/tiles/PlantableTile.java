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

        // this.growCountdown = -1;
        this.growCountdown = new Countdown();
        this.tileType = tileType;
        this.plantedVegetableCount = 0;
    }

    public String startNextTurn() {
        if (!this.growCountdown.isActive()) {
            return null;
        }

        if (!this.growCountdown.nextStep(-1)) {
            return null;
        }
        this.growCountdown.setValue(this.tileType.getMaxCapacity());

        int newGrownVegetables = this.plantedVegetableCount *= 2;
        if (newGrownVegetables >= this.tileType.getMaxCapacity()) {
            newGrownVegetables = this.tileType.getMaxCapacity();
            this.growCountdown.setActive(false);
        }

        this.plantedVegetableCount = newGrownVegetables;
        System.out.println(newGrownVegetables);

        // TODO set new value

        // this.growCountdown--;
        // if (growCountdown > 0) {
        // return null;
        // }

        // this.plantedVegetableCount *= 2;
        // if (this.plantedVegetableCount > this.tileType.getMaxCapacity()) {
        // this.plantedVegetableCount = this.tileType.getMaxCapacity();
        // }

        // // TODO reset countdown
        // // TODO duplicate vegtables if enough space
        // System.out.println(growCountdown);

        // return "The vegetables in your barn are spoiled.";
        return null;
    }

    // public int startNextRound() {
    // if (this.plantedVegetableCount == this.tileType.getMaxCapacity()) {
    // return 0;
    // }

    // this.growCountdown -= 1;

    // // TODO Check if 0 is the right value
    // if (this.growCountdown != 0) {
    // return 0;
    // }

    // // TODO reset grow countdown when not full

    // int grownVegetableCount = 0;
    // if (this.plantedVegetableCount * 2 > this.tileType.getMaxCapacity()) {
    // grownVegetableCount = this.tileType.getMaxCapacity() -
    // this.plantedVegetableCount;
    // this.plantedVegetableCount = this.tileType.getMaxCapacity();
    // } else {
    // grownVegetableCount = this.plantedVegetableCount;
    // this.plantedVegetableCount *= 2;
    // }
    // return grownVegetableCount;
    // }

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
