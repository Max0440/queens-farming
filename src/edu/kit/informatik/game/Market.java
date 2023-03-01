package edu.kit.informatik.game;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.kit.informatik.game.type.VegetableType;
import edu.kit.informatik.util.MapUtil;

/**
 * Represents the market of the game queens farming.
 * 
 * @author uiljo
 * @version 1.0
 */
public class Market {
    private static final int[] MUSHROOM_PRICES = {12, 15, 16, 17, 20};
    private static final int[] CARROT_PRICES = {3, 2, 2, 2, 1};
    private static final int[] TOMATO_PRICES = {3, 5, 6, 7, 9};
    private static final int[] SALAD_PRICES = {6, 5, 4, 3, 2};

    private static final int MAX_INDEX = 4;
    private static final int MIN_INDEX = 0;
    private static final int DEFAULT_START_INDEX = 2;

    private final Map<VegetableType, Integer> soldVegetables;
    private int mushroomCarrotIndex;
    private int tomatoSaladIndex;

    /**
     * Instantiates a new {@link Market}.
     */
    public Market() {
        this.mushroomCarrotIndex = DEFAULT_START_INDEX;
        this.tomatoSaladIndex = DEFAULT_START_INDEX;

        this.soldVegetables = new EnumMap<>(VegetableType.class);
        MapUtil.setVegetablesToValue(this.soldVegetables, 0);
    }

    /**
     * Sells a given vegetable and adds it to a list of sold vegetables, so that the
     * price can change after the round.
     * 
     * @param vegetable The type of vegetable to sell.
     * @return The current price of the sold vegetable.
     */
    public int sell(final VegetableType vegetable) {
        final int currentCount = this.soldVegetables.get(vegetable);
        this.soldVegetables.put(vegetable, currentCount + 1);

        return this.getPrice(vegetable);
    }

    /**
     * Handles all action needed after one turn.
     * Automatically changes prices depending on sold vegetables.
     */
    public void startNextTurn() {
        final int mushroomCount = this.soldVegetables.get(VegetableType.MUSHROOM);
        final int carrotCount = this.soldVegetables.get(VegetableType.CARROT);
        final int tomatoCount = this.soldVegetables.get(VegetableType.TOMATO);
        final int saladCount = this.soldVegetables.get(VegetableType.SALAD);

        if (mushroomCount - carrotCount >= 2) {
            int pairs = (mushroomCount - carrotCount) / 2;
            this.mushroomCarrotIndex -= pairs;
        } else if (carrotCount - mushroomCount >= 2) {
            int pairs = (carrotCount - mushroomCount) / 2;
            this.mushroomCarrotIndex += pairs;
        }
        if (tomatoCount - saladCount >= 2) {
            int pairs = (tomatoCount - saladCount) / 2;
            this.tomatoSaladIndex -= pairs;
        } else if (saladCount - tomatoCount >= 2) {
            int pairs = (saladCount - tomatoCount) / 2;
            this.tomatoSaladIndex += pairs;
        }

        // check that all indices are in the right range
        this.mushroomCarrotIndex = Math.min(this.mushroomCarrotIndex, MAX_INDEX);
        this.mushroomCarrotIndex = Math.max(MIN_INDEX, this.mushroomCarrotIndex);
        this.tomatoSaladIndex = Math.min(this.tomatoSaladIndex, MAX_INDEX);
        this.tomatoSaladIndex = Math.max(MIN_INDEX, this.tomatoSaladIndex);

        // clear map
        MapUtil.setVegetablesToValue(this.soldVegetables, 0);
    }

    /**
     * Returns the current price of a given vegetable at the market.
     * 
     * @param vegetable The type of vegetable to get the price from.
     * @return The current price or 0 if price isn't known.
     */
    public int getPrice(final VegetableType vegetable) {
        switch (vegetable) {
            case MUSHROOM:
                return MUSHROOM_PRICES[this.mushroomCarrotIndex];

            case CARROT:
                return CARROT_PRICES[this.mushroomCarrotIndex];

            case SALAD:
                return SALAD_PRICES[this.tomatoSaladIndex];

            case TOMATO:
                return TOMATO_PRICES[this.tomatoSaladIndex];

            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        Map<VegetableType, Integer> data = new LinkedHashMap<>();
        data.put(VegetableType.MUSHROOM, MUSHROOM_PRICES[this.mushroomCarrotIndex]);
        data.put(VegetableType.CARROT, CARROT_PRICES[this.mushroomCarrotIndex]);
        data.put(VegetableType.TOMATO, TOMATO_PRICES[this.tomatoSaladIndex]);
        data.put(VegetableType.SALAD, SALAD_PRICES[this.tomatoSaladIndex]);

        return MapUtil.formatToTable(data);
    }
}
