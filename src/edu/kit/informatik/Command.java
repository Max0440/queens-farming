package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {

    END_TURN("end turn") {
        @Override
        public String execute(Matcher input, Game game) {
            return game.endTurn();
        }
    },

    SHOW_BARN("show barn") {
        @Override
        public String execute(Matcher input, Game game) {
            return game.showBarn();
        }
    },

    SHOW_BOARD("show board") {
        @Override
        public String execute(Matcher input, Game game) {
            return game.showBoard();
        }
    },

    SHOW_MARKET("show market") {
        @Override
        public String execute(Matcher input, Game game) {
            return game.showMarket();
        }
    },

    // TODO? auch zulassen, dass man nur "sell" schreiben darf
    SELL("sell( ((carrot)|(mushroom)|(tomato)|(salad)))*") {
        @Override
        public String execute(Matcher input, Game game) {
            // TODO somehow with token stream
            String[] parameters = input.group().split(" ");
            List<VegetableType> vegetableList = new ArrayList<>();

            for (int i = 1; i < parameters.length; i++) {
                VegetableType vegetable = VegetableType.fromString(parameters[i]);
                vegetableList.add(vegetable);
            }

            return game.sell(vegetableList);
        }
    },

    BUY_VEGETABLE("buy vegetable ((carrot)|(mushroom)|(tomato)|(salad))") {
        @Override
        public String execute(Matcher input, Game game) {
            // TODO somehow with token stream & remove duplicate code
            String[] parameters = input.group().split(" ");

            if (parameters.length != 3) {
                throw new IllegalArgumentException();
            }
            VegetableType vegetable = VegetableType.fromString(parameters[2]);

            return game.buyVegetable(vegetable);
        }
    },

    BUY_LAND("buy land( -?[0-9]+){2}") {
        @Override
        public String execute(Matcher input, Game game) {
            String[] parameters = input.group().split(" ");

            if (parameters.length != 4) {
                throw new IllegalArgumentException();
            }

            return game.buyLand(Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]));
        }
    },

    // TODO implement
    HARVEST("harvest( -?[0-9]+){2}( [1-9][0-9]*)") {
        @Override
        public String execute(Matcher input, Game game) {
            System.out.println(input);
            return null;
        }
    },

    // TODO implement
    PLANT("plant( -?[0-9]+){2} ((carrot)|(mushroom)|(tomato)|(salad))") {
        @Override
        public String execute(Matcher input, Game game) {
            String[] parameters = input.group().split(" ");

            if (parameters.length != 4) {
                throw new IllegalArgumentException();
            }

            return game.plant(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), VegetableType.fromString(parameters[3]));
        }
    },

    QUIT("quit") {
        @Override
        public String execute(Matcher input, Game game) {
            game.quit();
            return null;
        }
    };

    private Pattern pattern;

    /**
     * Instantiates a new {@link Command} enum.
     * 
     * @param pattern regex pattern the command should listen to
     */
    Command(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Executes the right command and returns the result of the command or an error
     * message
     * 
     * @param input command string
     * @param game  {@link Game} object
     * @return the response to print to the user or an error message
     */
    public static String executeCommand(String input, Game game) {
        for (Command command : Command.values()) {
            Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                try {
                    return command.execute(matcher, game);
                } catch (IllegalArgumentException e) {
                    // TODO handle error
                    return "INVALID ARGUMENT DINS BUMS";
                }
            }
        }
        return Config.ERROR_COMMAND_NOT_FOUND;
    }

    abstract String execute(Matcher input, Game game);
}
