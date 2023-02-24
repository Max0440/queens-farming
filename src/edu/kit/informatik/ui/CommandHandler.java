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

public enum CommandHandler {

    END_TURN("end turn") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.endTurn();
        }
    },

    SHOW_BARN("show barn") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.showBarn();
        }
    },

    SHOW_BOARD("show board") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.showBoard();
        }
    },

    SHOW_MARKET("show market") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.showMarket();
        }
    },

    SELL_ALL("sell all") {
        @Override
        public String execute(Matcher input, QueensFarming game) {
            return game.sellAll();
        }
    },

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

    BUY_VEGETABLE("buy vegetable ((carrot)|(mushroom)|(tomato)|(salad))") {
        @Override
        public String execute(Matcher input, QueensFarming game) throws GameException {
            String[] parameters = input.group().split("\s");
            VegetableType vegetable = VegetableType.fromString(parameters[2]);

            return game.buyVegetable(vegetable);
        }
    },

    BUY_LAND("buy land( -?[0-9]+){2}") {
        @Override
        public String execute(Matcher input, QueensFarming game) throws GameException {
            String[] parameters = input.group().split("\s");
            int xCoordinate = Integer.parseInt(parameters[2]);
            int yCoordinate = Integer.parseInt(parameters[3]);

            return game.buyLand(xCoordinate, yCoordinate);
        }
    },

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
     * Executes the right command and returns the result of the command or an error
     * message
     * 
     * @param input command string
     * @param game  {@link QueensFarming} object
     * @return the response to print to the user or an error message
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

    abstract String execute(Matcher input, QueensFarming game);
}
