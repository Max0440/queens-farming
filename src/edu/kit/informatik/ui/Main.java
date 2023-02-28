package edu.kit.informatik.ui;

import java.util.Scanner;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.config.GeneralConfig;
import edu.kit.informatik.game.QueensFarming;

/**
 * The entry point of the game.
 * 
 * @author uiljo
 * @version 1.0
 */
public final class Main {

    private static final String VALID_PLAYER_NAME_REGEX = "[A-Za-z]+";

    private static Scanner inputScanner = new Scanner(System.in);
    private static QueensFarming game;

    private Main() {
        throw new AssertionError(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * The entry point of the game.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(ErrorMessages.ILLEGAL_ARGS_COUNT);
            return;
        }

        initializeSequence();

        while (game.isActive()) {
            String turnInformation = game.startNextTurn();
            if (turnInformation != null) {
                System.out.println(turnInformation);
            }

            while (game.isTurnRunning()) {
                String input = inputScanner.nextLine();

                String output = CommandHandler.executeCommand(input, game);
                if (output != null) {
                    System.out.println(output);
                }
            }
        }

        System.out.println(game.endGame());

        inputScanner.close();
    }

    private static void initializeSequence() {
        System.out.println(GeneralConfig.PIXEL_ART);

        System.out.println("How many players?");
        Integer playerCount = null;
        while (playerCount == null) {
            String playerCountString = inputScanner.nextLine();

            try {
                playerCount = Integer.parseInt(playerCountString);

                if (playerCount <= 0) {
                    System.err.println(ErrorMessages.INPUT_SMALLER_ONE);
                    playerCount = null;
                }
            } catch (NumberFormatException e) {
                System.err.println(ErrorMessages.INPUT_NOT_NUMBER);
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
                    System.err.println(ErrorMessages.PLAYER_NAME_NOT_VALID);
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
                    System.err.println(ErrorMessages.INPUT_SMALLER_ZERO);
                    goldAtStart = null;
                }
            } catch (NumberFormatException e) {
                System.err.println(ErrorMessages.INPUT_NOT_NUMBER);
            }
        }

        System.out.println("With how much gold should a player win?");
        Integer goldToWin = null;
        while (goldToWin == null) {
            String goldToWinString = inputScanner.nextLine();

            try {
                goldToWin = Integer.parseInt(goldToWinString);

                if (goldToWin < 1) {
                    System.err.println(ErrorMessages.INPUT_SMALLER_ONE);
                    goldToWin = null;
                }
            } catch (NumberFormatException e) {
                System.err.println(ErrorMessages.INPUT_NOT_NUMBER);
            }
        }

        System.out.println("Please enter the seed used to shuffle the tiles:");
        Integer seed = null;
        while (seed == null) {
            String seedString = inputScanner.nextLine();

            try {
                seed = Integer.parseInt(seedString);
            } catch (NumberFormatException e) {
                System.err.println(ErrorMessages.INPUT_NOT_NUMBER);
            }
        }

        game = new QueensFarming(playerNames, goldAtStart, goldToWin, seed);
    }
}
