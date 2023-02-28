package edu.kit.informatik.game.board;

import edu.kit.informatik.config.BoardConfig;
import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.type.PlantableTileType;
import edu.kit.informatik.game.type.VegetableType;
import edu.kit.informatik.util.Countdown;

/**
 * Represents a plantable tile in queens farming
 * 
 * @author uiljo
 * @version 1.0
 */
public class PlantableTile {

    private final PlantableTileType tileType;
    private VegetableType plantedVegetable;
    private int plantedVegetableCount;
    private final Countdown growCountdown;

    /**
     * Instantiates a new {@link PlantableTile}.
     * 
     * @param tileType The type of the plantable tile.
     */
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

    /**
     * Grows a planted vegetable on the tile us the countdown is active and returns
     * the amount of newly grown vegetables.
     * 
     * @return the amount of newly grown vegetables.
     */
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

    /**
     * Plants a given vegetable on the tile if it isn't already planted and the
     * vegetable is plantable on the tile
     * 
     * @param vegetable The type of vegetable to be planted.
     * @throws GameException when the tile has already been planted or the vegetable
     *                       can't be planted on the tile.
     */
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

    /**
     * Harvests a given amount of vegetables from the tile and returns the type of
     * vegetable harvested.
     * 
     * @param amountToHarvest The amount of vegetable to be harvested.
     * @return The type of vegetable harvested.
     * @throws GameException when the amount to harvest is not valid (below 1 or
     *                       more then planted).
     */
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
        String firstRowRepresentation;
        if (this.growCountdown.isActive()) {
            firstRowRepresentation = String.format(this.tileType.getBoardRepresentation(),
                    this.growCountdown.getValue());
        } else {
            firstRowRepresentation = String.format(this.tileType.getBoardRepresentation(), "*");
        }

        String middleRowRepresentation;
        if (this.plantedVegetableCount > 0) {
            middleRowRepresentation = String.format(BoardConfig.TILE_PLANTED_VEGETABLE,
                    this.plantedVegetable.getAbbreviation());
        } else {
            middleRowRepresentation = BoardConfig.EMPTY_ROW;
        }

        String lastRowRepresentation = String.format(BoardConfig.TILE_CAPACITY, this.plantedVegetableCount,
                this.tileType.getMaxCapacity());

        return new char[][] {
                firstRowRepresentation.toCharArray(),
                middleRowRepresentation.toCharArray(),
                lastRowRepresentation.toCharArray(),
        };
    }
}
