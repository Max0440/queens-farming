package edu.kit.informatik;

public class Config {
    public static final String ERROR = "Error: ";

    public static final String ERROR_ILLEGAL_ARGS_COUNT = ERROR + "Expected no arguments!";
    public static final String ERROR_UTILITY_CLASS_INSTANTIATION = ERROR + "Utility class may not be instantiated!";

    public static final String ERROR_PLAYER_COUNT_TO_SMALL = ERROR + "Player count must be bigger then 0!";
    public static final String ERROR_PLAYER_NAME_NOT_VALID = ERROR
            + "Player name must contain at least one uppercase or lowercase letter!";
    public static final String ERROR_INPUT_NOT_NUMBER = ERROR + "Input must be a number!";
    public static final String ERROR_INPUT_SMALLER_ONE = ERROR + "Input must be bigger then zero!";
    public static final String ERROR_INPUT_SMALLER_ZERO = ERROR + "Input must be bigger or equal to zero!";
    public static final String ERROR_LAND_NOT_PLACABLE = ERROR + "You can't buy land at this location!";
    public static final String ERROR_LAND_TO_EXPENSIVE = ERROR + "You don't have enough gold to buy the land!";

    public static final String ERROR_COMMAND_NOT_FOUND = ERROR + "Command not found!";
    public static final String ERROR_NOT_ENOUGH_GOLD = ERROR + "Not enough gold!";

    public static final VegetableType[] GARDEN_PLANTABLE_VEGETABLES = {
            VegetableType.CARROT,
            VegetableType.MUSHROOM,
            VegetableType.SALAD,
            VegetableType.TOMATO
    };
    public static final VegetableType[] FIELD_PLANTABLE_VEGETABLES = {
            VegetableType.CARROT,
            VegetableType.SALAD,
            VegetableType.TOMATO
    };
    public static final VegetableType[] FORREST_PLANTABLE_VEGETABLES = {
            VegetableType.CARROT,
            VegetableType.MUSHROOM
    };

    private Config() {
        throw new AssertionError(Config.ERROR_UTILITY_CLASS_INSTANTIATION);
    }
}
