package edu.kit.informatik.tiles;

import edu.kit.informatik.VegetableType;

public class PlantableTile extends Tile {

    private final PlantableTileType tileType;
    private VegetableType plantedVegetable;
    private int plantedVegetableCount;
    private int growCountdown;

    public PlantableTile(final int xCoordinate, final int yCoordinate, final PlantableTileType tileType) {
        super(xCoordinate, yCoordinate);

        this.tileType = tileType;
        this.plantedVegetableCount = 0;
    }

    public String startNextTurn() {
        if (plantedVegetableCount == 0) {
            return null;
        }

        this.growCountdown--;

        if (growCountdown > 0) {
            return null;
        }

        // TODO reset countdown
        // TODO duplicate vegtables if enough space
        System.out.println(growCountdown);

        return "The vegetables in your barn are spoiled.";
    }

    // public int startNextRound() {
    //     if (this.plantedVegetableCount == this.tileType.getMaxCapacity()) {
    //         return 0;
    //     }

    //     this.growCountdown -= 1;

    //     // TODO Check if 0 is the right value
    //     if (this.growCountdown != 0) {
    //         return 0;
    //     }

    //     // TODO reset grow countdown when not full

    //     int grownVegetableCount = 0;
    //     if (this.plantedVegetableCount * 2 > this.tileType.getMaxCapacity()) {
    //         grownVegetableCount = this.tileType.getMaxCapacity() - this.plantedVegetableCount;
    //         this.plantedVegetableCount = this.tileType.getMaxCapacity();
    //     } else {
    //         grownVegetableCount = this.plantedVegetableCount;
    //         this.plantedVegetableCount *= 2;
    //     }
    //     return grownVegetableCount;
    // }

    public void plant(final VegetableType vegetable) {
        if (this.plantedVegetableCount > 0) {
            // TODO Error
            return;
        }
        // TODO type is plantable?

        this.plantedVegetable = vegetable;
        this.plantedVegetableCount = 1;
        this.growCountdown = vegetable.getTimeToGrow();
    }

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

        if (this.plantedVegetableCount > 0) {
            c[0] = String.format("| %s %s |", this.tileType.getAbbreviation(), this.growCountdown).toCharArray();
            c[1] = String.format("|  %s  |", this.plantedVegetable.getAbbreviation()).toCharArray();
        } else {
            c[0] = String.format("| %s * |", this.tileType.getAbbreviation()).toCharArray();
            c[1] = "|     |".toCharArray();
        }

        c[2] = String.format("| %d/%d |", this.plantedVegetableCount, this.tileType.getMaxCapacity()).toCharArray();
        return c;
    }
}
