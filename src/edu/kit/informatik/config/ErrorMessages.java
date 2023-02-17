package edu.kit.informatik.config;

public class ErrorMessages {

        /**
         * Error message in case the program is started with arguments
         */
        public static final String ILLEGAL_ARGS_COUNT = GeneralConfig.ERROR + "Expected no arguments!";
        /**
         * Error message when trying to initialize a utility class
         */
        public static final String UTILITY_CLASS_INSTANTIATION = GeneralConfig.ERROR
                        + "Utility class may not be instantiated!";

        /**
         * Error message in case a input is expected to be a number
         */
        public static final String INPUT_NOT_NUMBER = GeneralConfig.ERROR + "Input must be a number!";
        /**
         * Error message in case a number has to be bigger the zero
         */
        public static final String INPUT_SMALLER_ONE = GeneralConfig.ERROR + "Input must be bigger then zero!";
        /**
         * Error message in case a number has to be bigger or equal to zero
         */
        public static final String INPUT_SMALLER_ZERO = GeneralConfig.ERROR
                        + "Input must be bigger or equal to zero!";
        /**
         * Error message in case the seed is not in the interval
         * [-2147483648,2147483647]
         */
        public static final String SEED_NOT_IN_INTERVAL = GeneralConfig.ERROR
                        + "Seed must be in the interval [-2147483648,2147483647]!";
        /**
         * Error message in case a command is not known
         */
        public static final String COMMAND_NOT_FOUND = GeneralConfig.ERROR + "Command not found!";
        /**
         * Error message in case a entered player name is not valid
         */
        public static final String PLAYER_NAME_NOT_VALID = GeneralConfig.ERROR
                        + "Player name must contain at least one uppercase or lowercase letter!";
        /**
         * Error message in case a player doesn't have enough
         */
        public static final String NOT_ENOUGH_GOLD = GeneralConfig.ERROR + "You don't have enough gold!";

        /**
         * Error message in case of tying to buy land at a position where it can't be
         * bought
         */
        public static final String LAND_NOT_PLACABLE = GeneralConfig.ERROR
                        + "You can't buy land at this location!";
        /**
         * Error message in case of tying to plant or harvest something at a location
         * where the players doesn't have land
         */
        public static final String LAND_NOT_OWNED = GeneralConfig.ERROR + "You don't own land at this location!";

        /**
         * Error message in case a player tries to plant a vegetable they doesn't have
         */
        public static final String VEGETABLE_NOT_OWNED = GeneralConfig.ERROR + "You don't own the vegetable!";
        /**
         * Error message in case a player tries to plant something on a field that is
         * already planted
         */
        public static final String ALREADY_PLANTED = GeneralConfig.ERROR
                        + "Something is already planted in the field!";
        /**
         * Error message in case a player tries to plant a wrong vegetable type on a
         * field
         */
        public static final String WRONG_VEGETABLE_TYPE = GeneralConfig.ERROR
                        + "You can't plant this vegetable on this field type!";
        /**
         * Error message in case a player tries to plant a vegetable they doesn't have
         */
        public static final String TILE_STACK_EMPTY = GeneralConfig.ERROR + "There is no more land to buy!";

        private ErrorMessages() {
                throw new AssertionError(UTILITY_CLASS_INSTANTIATION);
        }
}
