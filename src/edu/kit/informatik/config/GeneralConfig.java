package edu.kit.informatik.config;

import edu.kit.informatik.game.type.VegetableType;

public class GeneralConfig {

    /**
     * The start of every error output
     */
    public static final String ERROR = "Error: ";

    /**
     * Allowed vegetable types to plant in a garden
     */
    public static final VegetableType[] GARDEN_PLANTABLE_VEGETABLES = {
            VegetableType.CARROT,
            VegetableType.MUSHROOM,
            VegetableType.SALAD,
            VegetableType.TOMATO
    };
    /**
     * Allowed vegetable types to plant on a (large) field
     */
    public static final VegetableType[] FIELD_PLANTABLE_VEGETABLES = {
            VegetableType.CARROT,
            VegetableType.SALAD,
            VegetableType.TOMATO
    };
    /**
     * Allowed vegetable types to plant in a (large) forest
     */
    public static final VegetableType[] FOREST_PLANTABLE_VEGETABLES = {
            VegetableType.CARROT,
            VegetableType.MUSHROOM
    };

    public static final String PIXEL_ART = new StringBuilder()
            .append("                           _.-^-._    .--.    ").append(System.lineSeparator())
            .append("                        .-'   _   '-. |__|    ").append(System.lineSeparator())
            .append("                       /     |_|     \\|  |    ").append(System.lineSeparator())
            .append("                      /               \\  |    ").append(System.lineSeparator())
            .append("                     /|     _____     |\\ |    ").append(System.lineSeparator())
            .append("                      |    |==|==|    |  |    ").append(System.lineSeparator())
            .append("  |---|---|---|---|---|    |--|--|    |  |    ").append(System.lineSeparator())
            .append("  |---|---|---|---|---|    |==|==|    |  |    ").append(System.lineSeparator())
            .append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^").append(System.lineSeparator())
            .append("^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^").append(System.lineSeparator())
            .append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^").toString();

    private GeneralConfig() {
        throw new AssertionError(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
    }
}
