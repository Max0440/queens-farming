package edu.kit.informatik.game.board;

import java.util.EnumMap;
import java.util.Map;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.type.VegetableType;
import edu.kit.informatik.util.Countdown;
import edu.kit.informatik.util.MapUtil;

public class Barn {

    private static final int ROUNDS_UNTIL_ROTTEN = 6;

    private Countdown countdown;
    private final Map<VegetableType, Integer> vegetables;

    /**
     * Instantiates a new {@link Barn}
     * 
     * @param column on board
     * @param row    on board
     */
    public Barn() {
        this.countdown = new Countdown(ROUNDS_UNTIL_ROTTEN + 1, 0, true);
        this.vegetables = new EnumMap<>(VegetableType.class);

        MapUtil.setVegetablesToValue(this.vegetables, 1);
    }

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
     * @param vegetable to remove
     * @throws GameException when vegetable is not in barn
     */
    public void removeVegetable(final VegetableType vegetable) throws GameException {
        int currentCount = this.vegetables.get(vegetable);
        if (currentCount == 0) {
            throw new GameException(ErrorMessages.VEGETABLE_NOT_OWNED);
        }
        this.vegetables.put(vegetable, currentCount - 1);

        if (this.getTotalVegetableCount() == 0) {
            this.stopCountdown();
        }
    }

    /**
     * Adds a given vegetable to the barn
     * 
     * @param vegetable to add
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
     * Handles next turn
     * Checks if vegetables are spoiled and returns a message
     * 
     * @return a message if vegetables are spoiled or null if not
     */
    public String startNextTurn() {
        if (!this.countdown.isActive()) {
            return null;
        }

        if (!this.countdown.nextStep(-1)) {
            return null;
        }

        this.stopCountdown();
        MapUtil.setVegetablesToValue(this.vegetables, 0);
        return "The vegetables in your barn are spoiled.";
    }

    /**
     * Checks if a given vegetable is in the barn
     * 
     * @param vegetable to check
     * @return {@code true} if vegetable is in the barn, {@code false} if not
     */
    public boolean hasInBarn(VegetableType vegetable) {
        return this.vegetables.get(vegetable) > 0;
    }

    public char[][] toCharArray() {
        char[][] charArray = new char[3][7];

        charArray[0] = "|     |".toCharArray();
        charArray[2] = "|     |".toCharArray();
        if (this.getTotalVegetableCount() == 0) {
            charArray[1] = "| B * |".toCharArray();
        } else {
            charArray[1] = String.format("| B %d |", this.countdown.getValue()).toCharArray();
        }

        return charArray;
    }

    // TODO name
    public String toStringTodo(int gold) {
        StringBuilder sb = new StringBuilder();

        if (this.getTotalVegetableCount() == 0) {
            sb.append("Barn");
            sb.append(System.lineSeparator());
            sb.append("Gold: ");
            sb.append(gold);
            return sb.toString();
        }

        sb.append(String.format("Barn (spoils in %d turns)", this.countdown.getValue()));
        sb.append(System.lineSeparator());

        Map<VegetableType, Integer> sortedMap = MapUtil.sortByInt(this.vegetables);
        sb.append(MapUtil.formatToTable(sortedMap, gold));
        return sb.toString();
    }
}