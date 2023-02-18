package edu.kit.informatik.game.tiles;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.Countdown;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.VegetableType;

public class PlantableTile extends Tile {

    private final PlantableTileType tileType;
    private VegetableType plantedVegetable;
    private int plantedVegetableCount;
    private Countdown growCountdown;

    public PlantableTile(final int xCoordinate, final int yCoordinate, final PlantableTileType tileType) {
        super(xCoordinate, yCoordinate);

        this.growCountdown = new Countdown();
        this.tileType = tileType;
        this.plantedVegetableCount = 0;
    }

    private void startCountdown() {
        this.growCountdown.setValue(this.plantedVegetable.getTimeToGrow());
        this.growCountdown.setMinValue(0);
        this.growCountdown.setActive(true);
    }

    private void stopCountdown() {
        this.growCountdown.setActive(false);
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
            this.stopCountdown();
        }

        final int newlyGrownVegetable = newVegetableCount - this.plantedVegetableCount;
        this.plantedVegetableCount = newVegetableCount;
        return newlyGrownVegetable;
    }

    public void plant(final VegetableType vegetable) throws GameException {
        if (this.plantedVegetableCount > 0) {
            throw new GameException(ErrorMessages.ALREADY_PLANTED);
        }

        if (!this.tileType.isPlantableVegetable(vegetable)) {
            throw new GameException(ErrorMessages.WRONG_VEGETABLE_TYPE);
        }

        this.plantedVegetable = vegetable;
        this.plantedVegetableCount = 1;
        this.startCountdown();
    }

    // TODO deactivate countdown
    public VegetableType harvest(final int amountToHarvest) throws GameException {
        if (amountToHarvest < 1) {
            // TODO Error
            throw new GameException("Error: count bigger 0");
        }
        if (this.plantedVegetableCount < amountToHarvest) {
            // TODO Error
            throw new GameException("Error: amount to harvest can't be smaller than plant count");
        }

        this.plantedVegetableCount -= amountToHarvest;
        if (this.plantedVegetableCount == 0) {
            this.stopCountdown();
        } else if (this.plantedVegetableCount > 0 && !this.growCountdown.isActive()) {
            this.startCountdown();
        }

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
