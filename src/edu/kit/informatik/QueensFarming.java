package edu.kit.informatik;

import java.util.Scanner;

/**
 * The entry point of the game.
 * 
 * @author uiljo
 * @version 1.0
 */
public class QueensFarming {

    private static final String VALID_PLAYER_NAME_REGEX = "[A-Za-z]+";

    private static Scanner inputScanner = new Scanner(System.in);
    private static Game game;

    private QueensFarming() {
        throw new AssertionError(Config.ERROR_UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * The entry point of the game.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(Config.ERROR_ILLEGAL_ARGS_COUNT);
        }

        initializeSequence();
        // String[] players = { "Mira", "Milan", "Vincent" };
        // String[] players = { "Max", "Jean" };
        // String[] players = { "Mira" };
        // game = new Game(players, 20, 30, 6);

        while (game.isActive()) {
            System.out.println(game.startNextTurn());

            while (game.isTurnRunning()) {
                String input = inputScanner.nextLine();

                String output = Command.executeCommand(input, game);
                if (output != null) {
                    System.out.println(output);
                }
            }
        }

        inputScanner.close();
    }

    private static void initializeSequence() {
        printPixelArt();

        System.out.println("How many players?");
        Integer playerCount = null;
        while (playerCount == null) {
            String playerCountString = inputScanner.nextLine();

            try {
                playerCount = Integer.parseInt(playerCountString);

                if (playerCount <= 0) {
                    System.err.println(Config.ERROR_INPUT_SMALLER_ONE);
                    playerCount = null;
                }
            } catch (NumberFormatException e) {
                System.err.println(Config.ERROR_INPUT_NOT_NUMBER);
            }
        }

        String[] playerNames = new String[playerCount];
        for (int i = 0; i < playerCount; i++) {
            System.out.println(String.format("Enter the name of player %d:", i + 1));

            while (playerNames[i] == null) {
                String name = inputScanner.nextLine();

                if (name.matches(VALID_PLAYER_NAME_REGEX)) {
                    playerNames[i] = name;
                } else {
                    System.err.println(Config.ERROR_PLAYER_NAME_NOT_VALID);
                }
            }
        }

        System.out.println("With how much gold should each player start?");
        Integer goldAtStart = null;
        while (goldAtStart == null) {
            String goldAtStartString = inputScanner.nextLine();

            try {
                goldAtStart = Integer.parseInt(goldAtStartString);

                if (goldAtStart < 0) {
                    System.err.println(Config.ERROR_INPUT_SMALLER_ZERO);
                    goldAtStart = null;
                }
            } catch (NumberFormatException e) {
                System.err.println(Config.ERROR_INPUT_NOT_NUMBER);
            }
        }

        System.out.println("With how much gold should a player win?");
        Integer goldToWin = null;
        while (goldToWin == null) {
            String goldToWinString = inputScanner.nextLine();

            try {
                goldToWin = Integer.parseInt(goldToWinString);

                if (goldToWin < 1) {
                    System.err.println(Config.ERROR_INPUT_SMALLER_ONE);
                    goldToWin = null;
                }
            } catch (NumberFormatException e) {
                System.err.println(Config.ERROR_INPUT_NOT_NUMBER);
            }
        }

        System.out.println("Please enter the seed used to shuffle the tiles:");
        Integer seed = null;
        while (seed == null) {
            String seedString = inputScanner.nextLine();

            try {
                seed = Integer.parseInt(seedString);
            } catch (NumberFormatException e) {
                System.err.println(Config.ERROR_INPUT_NOT_NUMBER);
            }
        }

        game = new Game(playerNames, goldAtStart, goldToWin, seed);
    }

    private static void printPixelArt() {
        System.out.println("                           _.-^-._    .--.");
        System.out.println("                        .-'   _   '-. |__|");
        System.out.println("                       /     |_|     \\|  |");
        System.out.println("                      /               \\  |");
        System.out.println("                     /|     _____     |\\ |");
        System.out.println("                      |    |==|==|    |  |");
        System.out.println("  |---|---|---|---|---|    |--|--|    |  |");
        System.out.println("  |---|---|---|---|---|    |==|==|    |  |");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
}
