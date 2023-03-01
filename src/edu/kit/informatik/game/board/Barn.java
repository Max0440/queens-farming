package edu.kit.informatik.game.board;

import java.util.EnumMap;
import java.util.Map;

import edu.kit.informatik.config.BoardConfig;
import edu.kit.informatik.game.type.VegetableType;
import edu.kit.informatik.util.Countdown;
import edu.kit.informatik.util.MapUtil;

/**
 * Represents a barn from a player in queens farming
 * 
 * @author uiljo
 * @version 1.0
 */
public class Barn {

    private static final int ROUNDS_UNTIL_ROTTEN = 6;

    private final Countdown countdown;
    private final Map<VegetableType, Integer> vegetables;

    /**
     * Instantiates a new {@link Barn}
     */
    public Barn() {
        this.countdown = new Countdown(ROUNDS_UNTIL_ROTTEN + 1, 0, true);
        this.vegetables = new EnumMap<>(VegetableType.class);

        MapUtil.setVegetablesToValue(this.vegetables, 1);
    }

    /**
     * Returns a map with the amount of vegetables the player has
     * 
     * @return vegetables
     */
    public Map<VegetableType, Integer> getVegetables() {
        return this.vegetables;
    }

    private int getTotalVegetableCount() {
        int count = 0;
        for (Integer value : this.vegetables.values()) {
            count += value;
        }
        return count;
    }

    private void startCountdown() {
        this.countdown.setValue(ROUNDS_UNTIL_ROTTEN);
        this.countdown.setActive(true);
    }

    private void stopCountdown() {
        this.countdown.setActive(false);
    }

    /**
     * Removes a given vegetable from the barn
     * 
     * @param vegetable Type of vegetable to be removed from the barn
     */
    public void removeVegetable(final VegetableType vegetable) {
        int currentCount = this.vegetables.get(vegetable);
        if (currentCount == 0) {
            return;
        }
        this.vegetables.put(vegetable, currentCount - 1);

        // stop countdown if barn is empty
        if (this.getTotalVegetableCount() == 0) {
            this.stopCountdown();
        }
    }

    /**
     * Adds a given vegetable to the barn
     * 
     * @param vegetable Type of vegetable to be added to the barn
     */
    public void addVegetable(final VegetableType vegetable) {
        final int currentCount = this.vegetables.get(vegetable);
        this.vegetables.put(vegetable, currentCount + 1);

        // start countdown if barn was empty before
        if (this.getTotalVegetableCount() == 1) {
            this.startCountdown();
        }
    }

    /**
     * Handles a turn in the game and returns a message indicating if the vegetables
     * are spoiled
     * 
     * @return a message indicating if the vegetables are spoiled, or {@code null}
     *         if the aren't
     */
    public String startNextTurn() {
        if (!this.countdown.isActive()) {
            return null;
        }

        if (!this.countdown.nextStep()) {
            return null;
        }

        this.stopCountdown();
        MapUtil.setVegetablesToValue(this.vegetables, 0);
        return "The vegetables in your barn are spoiled.";
    }

    /**
     * Checks if a given vegetable is in the barn
     * 
     * @param vegetable The type of vegetable to check
     * @return {@code true} if vegetable is in the barn, {@code false} if not
     */
    public boolean hasInBarn(VegetableType vegetable) {
        return this.vegetables.get(vegetable) > 0;
    }

    /**
     * Returns the representation of the barn as an 2d-array which can be formatted
     * with den BoardStringBuilder.
     * 
     * @return board representation of the barn.
     */
    public char[][] toCharArray() {
        String middleRowRepresentation;
        if (this.getTotalVegetableCount() == 0) {
            middleRowRepresentation = String.format(BoardConfig.BARN_COUNTDOWN, BoardConfig.STAR);
        } else {
            middleRowRepresentation = String.format(BoardConfig.BARN_COUNTDOWN, this.countdown.getValue());
        }

        return new char[][] {
                BoardConfig.EMPTY_ROW.toCharArray(),
                middleRowRepresentation.toCharArray(),
                BoardConfig.EMPTY_ROW.toCharArray(),
        };
    }

    /**
     * Returns a formatted string representation of the barn, including the rounds
     * until the vegetables spoil and all vegetables stored in it.
     * 
     * @param gold The current amount of gold the player has.
     * @return a formatted string representation of the barn.
     */
    public String toStringFormatted(int gold) {
        StringBuilder sb = new StringBuilder();

        if (this.getTotalVegetableCount() == 0) {
            sb.append("Barn");
            sb.append(System.lineSeparator());
            sb.append("Gold: ");
            sb.append(gold);
            return sb.toString();
        }

        if (this.countdown.getValue() == 1) {
            sb.append(String.format("Barn (spoils in %d turn)", this.countdown.getValue()));
        } else {
            sb.append(String.format("Barn (spoils in %d turns)", this.countdown.getValue()));
        }
        sb.append(System.lineSeparator());

        Map<VegetableType, Integer> sortedMap = MapUtil.sortByInt(this.vegetables);
        sb.append(MapUtil.formatToTable(sortedMap, gold));
        return sb.toString();
    }
}