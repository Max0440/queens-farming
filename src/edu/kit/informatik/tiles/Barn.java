package edu.kit.informatik.tiles;

import java.util.EnumMap;
import java.util.Map;

import edu.kit.informatik.VegetableType;

public class Barn extends Tile {

    private static final int ROUNDS_UNTIL_ROTTEN = 6;

    private int countdown = ROUNDS_UNTIL_ROTTEN;
    private final Map<VegetableType, Integer> vegetables;

    /**
     * Instantiates a new {@link Barn}
     * 
     * @param column on board
     * @param row    on board
     */
    // TODO warum nicht gleich null?
    public Barn(final int column, final int row) {
        super(column, row);

        this.vegetables = new EnumMap<>(VegetableType.class);
        setVegetableListToValue(1);
    }

    private int getTotalVegetableCount() {
        int count = 0;
        for (Integer value : this.vegetables.values()) {
            count += value;
        }
        return count;
    }

    private void setVegetableListToValue(final int value) {
        this.vegetables.put(VegetableType.CARROT, value);
        this.vegetables.put(VegetableType.MUSHROOM, value);
        this.vegetables.put(VegetableType.SALAD, value);
        this.vegetables.put(VegetableType.TOMATO, value);
    }

    /**
     * Removes a given vegetable from the barn
     * 
     * @param vegetable to remove
     * @throws IllegalArgumentException when vegetable is not in barn
     */
    public void removeVegetable(final VegetableType vegetable) throws IllegalArgumentException {
        int currentCount = this.vegetables.get(vegetable);
        if (currentCount == 0) {
            throw new IllegalArgumentException();
        }
        this.vegetables.put(vegetable, currentCount - 1);
        // TODO reset countdown when empty
    }

    /**
     * Adds a given vegetable to the barn
     * 
     * @param vegetable to add
     */
    public void addVegetable(final VegetableType vegetable) {
        final int currentCount = this.vegetables.get(vegetable);
        this.vegetables.put(vegetable, currentCount + 1);
    }

    /**
     * Handles next turn
     * Checks if vegetables are spoiled and returns a message
     * 
     * @return a message if vegetables are spoiled or null if not
     */
    public String startNextTurn() {
        if (this.getTotalVegetableCount() == 0) {
            return null;
        }

        this.countdown--;

        if (this.countdown != 0) {
            return null;
        }

        this.countdown = ROUNDS_UNTIL_ROTTEN;
        setVegetableListToValue(0);
        return "The vegetables in your barn are spoiled.";
    }

    public boolean hasInBarn(VegetableType vegetable) {
        return this.vegetables.get(vegetable) > 0;
    }

    @Override
    public char[][] toCharArray() {
        char[][] charArray = new char[3][7];

        charArray[0] = "|     |".toCharArray();
        charArray[2] = "|     |".toCharArray();
        if (this.getTotalVegetableCount() == 0) {
            charArray[1] = "| B * |".toCharArray();
        } else {
            charArray[1] = String.format("| B %d |", this.countdown).toCharArray();
        }

        return charArray;
    }

    @Override
    public String toString() {
        // TODO print
        if (this.getTotalVegetableCount() == 0) {
            return "Barn";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Barn (spoils in %d turns)", this.countdown));
        sb.append(System.lineSeparator());

        // sort
        // format
        // nice
        // align
        // dont show values with 0
        sb.append(VegetableType.CARROT.getPlural() + ": ");
        sb.append(this.vegetables.get(VegetableType.CARROT));
        sb.append(System.lineSeparator());

        sb.append(VegetableType.MUSHROOM.getPlural() + ": ");
        sb.append(this.vegetables.get(VegetableType.MUSHROOM));
        sb.append(System.lineSeparator());

        sb.append(VegetableType.SALAD.getPlural() + ": ");
        sb.append(this.vegetables.get(VegetableType.SALAD));
        sb.append(System.lineSeparator());

        sb.append(VegetableType.TOMATO.getPlural() + ": ");
        sb.append(this.vegetables.get(VegetableType.TOMATO));
        sb.append(System.lineSeparator());

        // right length
        sb.append("---------------");
        sb.append(System.lineSeparator());

        sb.append("Sum: ");
        sb.append(getTotalVegetableCount());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}