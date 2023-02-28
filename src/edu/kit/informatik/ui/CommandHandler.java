package edu.kit.informatik.ui;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.QueensFarming;
import edu.kit.informatik.game.type.VegetableType;
import edu.kit.informatik.util.MapUtil;

/**
 * Enum of all available commands in queens farming.
 */
public enum CommandHandler {

    /**
     * Ends the turn of the current player.
     */
    END_TURN("end turn") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.endTurn();
        }
    },

    /**
     * Returns the barn representation.
     */
    SHOW_BARN("show barn") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.showBarn();
        }
    },

    /**
     * Returns the board representation.
     */
    SHOW_BOARD("show board") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.showBoard();
        }
    },

    /**
     * Returns the market representation.
     */
    SHOW_MARKET("show market") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.showMarket();
        }
    },

    /**
     * Sells all vegetables of the current player.
     */
    SELL_ALL("sell all") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.sellAll();
        }
    },

    /**
     * Sells the given vegetables from the current player if possible.
     */
    SELL("sell( ((carrot)|(mushroom)|(tomato)|(salad)))*") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            String[] parameters = input.group().split("\s");
            Map<VegetableType, Integer> vegetableList = new EnumMap<>(VegetableType.class);
            MapUtil.setVegetablesToValue(vegetableList, 0);

            for (int i = 1; i < parameters.length; i++) {
                VegetableType vegetableType = VegetableType.fromString(parameters[i]);

                final int currentCount = vegetableList.get(vegetableType);
                vegetableList.put(vegetableType, currentCount + 1);
            }

            return game.sell(vegetableList);
        }
    },

    /**
     * Buys the given vegetable if possible.
     */
    BUY_VEGETABLE("buy vegetable ((carrot)|(mushroom)|(tomato)|(salad))") {
        @Override
        public String execute(Matcher input, QueensFarming game) throws GameException {
            String[] parameters = input.group().split("\s");
            VegetableType vegetable = VegetableType.fromString(parameters[2]);

            return game.buyVegetable(vegetable);
        }
    },

    /**
     * Buys land at the given location if possible.
     */
    BUY_LAND("buy land( -?[0-9]+){2}") {
        @Override
        public String execute(Matcher input, QueensFarming game) throws GameException {
            String[] parameters = input.group().split("\s");
            int xCoordinate = Integer.parseInt(parameters[2]);
            int yCoordinate = Integer.parseInt(parameters[3]);

            return game.buyLand(xCoordinate, yCoordinate);
        }
    },

    /**
     * Harvest the given amount of vegetables from the given location if possible.
     */
    HARVEST("harvest( -?[0-9]+){2}( [0-9]+)") {
        @Override
        public String execute(Matcher input, QueensFarming game) throws GameException {
            String[] parameters = input.group().split("\s");
            int xCoordinate = Integer.parseInt(parameters[1]);
            int yCoordinate = Integer.parseInt(parameters[2]);
            int amountToHarvest = Integer.parseInt(parameters[3]);

            return game.harvest(xCoordinate, yCoordinate, amountToHarvest);
        }
    },

    /**
     * Plants a given vegetable at a given location if possible.
     */
    PLANT("plant( -?[0-9]+){2} ((carrot)|(mushroom)|(tomato)|(salad))") {
        @Override
        public String execute(Matcher input, QueensFarming game) throws GameException {
            String[] parameters = input.group().split("\s");
            int xCoordinate = Integer.parseInt(parameters[1]);
            int yCoordinate = Integer.parseInt(parameters[2]);
            VegetableType vegetable = VegetableType.fromString(parameters[3]);

            return game.plant(xCoordinate, yCoordinate, vegetable);
        }
    },

    /**
     * Ends the game.
     */
    QUIT("quit") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            game.quit();
            return null;
        }
    };

    private Pattern pattern;

    /**
     * Instantiates a new {@link CommandHandler} enum
     * 
     * @param pattern regex pattern the command should listen to
     */
    CommandHandler(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Executes the input.
     * 
     * @param input The user input.
     * @param game  The queens farming game.
     * @return The result message, an error message or {@code null}
     */
    abstract String execute(Matcher input, QueensFarming game);

    /**
     * Executes the right command and returns the result of the command or an error
     * message.
     * 
     * @param input The user input.
     * @param game  {@link QueensFarming} object,
     * @return The response to print to the user or an error message or
     *         {@code null}.
     */
    public static String executeCommand(String input, QueensFarming game) {
        for (CommandHandler command : CommandHandler.values()) {
            Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                try {
                    return command.execute(matcher, game);
                } catch (GameException e) {
                    return e.getMessage();
                }
            }
        }
        return ErrorMessages.COMMAND_NOT_FOUND;
    }

}
