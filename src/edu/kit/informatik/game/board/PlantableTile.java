package edu.kit.informatik.game.board;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.type.PlantableTileType;
import edu.kit.informatik.game.type.VegetableType;
import edu.kit.informatik.util.Countdown;

public class PlantableTile {

    private final PlantableTileType tileType;
    private VegetableType plantedVegetable;
    private int plantedVegetableCount;
    private Countdown growCountdown;

    public PlantableTile(final PlantableTileType tileType) {
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

    public VegetableType harvest(final int amountToHarvest) throws GameException {
        if (amountToHarvest < 1) {
            throw new GameException(ErrorMessages.INPUT_SMALLER_ONE);
        }
        if (this.plantedVegetableCount < amountToHarvest) {
            throw new GameException(ErrorMessages.HARVEST_MORE_THEN_PLANTED);
        }

        this.plantedVegetableCount -= amountToHarvest;
        if (this.plantedVegetableCount == 0) {
            this.stopCountdown();
        } else if (this.plantedVegetableCount > 0 && !this.growCountdown.isActive()) {
            this.startCountdown();
        }

        return plantedVegetable;
    }

    public char[][] toCharArray() {
        char[][] c = new char[3][7];

        if (this.growCountdown.isActive()) {
            c[0] = String.format(this.tileType.getBoardRepresentation(), this.growCountdown.getValue()).toCharArray();
        } else {
            // TODO remove magic
            c[0] = String.format(this.tileType.getBoardRepresentation(), "*").toCharArray();
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
