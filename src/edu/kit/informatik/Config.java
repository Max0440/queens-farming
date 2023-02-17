package edu.kit.informatik;

public class Config {
    /**
     * The start of every error output
     */
    public static final String ERROR = "Error: ";

    /**
     * Error message in case the program is started with arguments
     */
    public static final String ERROR_ILLEGAL_ARGS_COUNT = ERROR + "Expected no arguments!";
    /**
     * Error message when trying to initialize a utility class
     */
    public static final String ERROR_UTILITY_CLASS_INSTANTIATION = ERROR + "Utility class may not be instantiated!";

    /**
     * Error message in case a input is expected to be a number
     */
    public static final String ERROR_INPUT_NOT_NUMBER = ERROR + "Input must be a number!";
    /**
     * Error message in case a number has to be bigger the zero
     */
    public static final String ERROR_INPUT_SMALLER_ONE = ERROR + "Input must be bigger then zero!";
    /**
     * Error message in case a number has to be bigger or equal to zero
     */
    public static final String ERROR_INPUT_SMALLER_ZERO = ERROR + "Input must be bigger or equal to zero!";
    /**
     * Error message in case a command is not known
     */
    public static final String ERROR_COMMAND_NOT_FOUND = ERROR + "Command not found!";
    /**
     * Error message in case a entered player name is not valid
     */
    public static final String ERROR_PLAYER_NAME_NOT_VALID = ERROR
            + "Player name must contain at least one uppercase or lowercase letter!";
    /**
     * Error message in case a player doesn't have enough
     */
    public static final String ERROR_NOT_ENOUGH_GOLD = ERROR + "You don't have enough gold!";

    /**
     * Error message in case of tying to buy land at a position where it can't be
     * bought
     */
    public static final String ERROR_LAND_NOT_PLACABLE = ERROR + "You can't buy land at this location!";
    /**
     * Error message in case of tying to plant or harvest something at a location
     * where the players doesn't have land
     */
    public static final String ERROR_LAND_NOT_OWNED = ERROR + "You don't own land at this location!";

    /**
     * Error message in case a player tries to plant a vegetable they doesn't have
     */
    public static final String ERROR_VEGETABLE_NOT_OWNED = ERROR + "You don't own the vegetable!";

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

    private Config() {
        throw new AssertionError(ERROR_UTILITY_CLASS_INSTANTIATION);
    }
}
