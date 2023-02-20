package edu.kit.informatik.config;

import edu.kit.informatik.type.VegetableType;

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

    // TODO spaces
    public static final String PIXEL_ART = """
                                       _.-^-._    .--.
                                    .-'   _   '-. |__|
                                   /     |_|     \\|  |
                                  /               \\  |
                                 /|     _____     |\\ |
                                  |    |==|==|    |  |
              |---|---|---|---|---|    |--|--|    |  |
              |---|---|---|---|---|    |==|==|    |  |
            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            ^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^
            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^""";

    private GeneralConfig() {
        throw new AssertionError(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
    }
}
