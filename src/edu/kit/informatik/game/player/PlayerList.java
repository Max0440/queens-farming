package edu.kit.informatik.game.player;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a list of all {@link Player} of the game queens farming.
 * 
 * @author uiljo
 * @version 1.0
 */
public class PlayerList {

    private final List<Player> players;

    /**
     * Instantiates a new {@link PlayerList}.
     * 
     * @param playerNames An array with all player names.
     * @param goldAtStart The amount of gold, the players have at the beginning.
     */
    public PlayerList(final String[] playerNames, final int goldAtStart) {
        this.players = new ArrayList<>();

        for (String playerName : playerNames) {
            this.players.add(new Player(playerName, goldAtStart));
        }
    }

    /**
     * Returns amount of players in the game.
     * 
     * @return The size of the player list.
     */
    public int size() {
        return players.size();
    }

    /**
     * Returns the current player based on the current turn.
     * 
     * @param currentTurn Turn index of the round.
     * @return The player whose turn it is.
     */
    public Player getCurrentPlayer(final int currentTurn) {
        return players.get(currentTurn);
    }

    /**
     * Checks if at least one player has more or the same amount of gold as given
     * and therefore won.
     * 
     * @param goldToWin The amount of gold needed to win.
     * @return {@code true} if someone won, {@code false} otherwise.
     */
    public boolean someoneWon(final int goldToWin) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getGold() >= goldToWin) {
                return true;
            }
        }
        return false;
    }

    /**
     * handles the end of the game. Determines the winner(s) of the game and returns
     * a message containing the players that won.
     * If no player has reached the amount of gold needed to win, the player(s) with
     * the highest amount of gold will win.
     * 
     * @param goldToWin The amount of gold a player needs to win the game
     * @return A string representation of the player(s) that won.
     */
    public String endGame(final int goldToWin) {
        List<Player> playerThatWon = new ArrayList<>();

        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getGold() >= goldToWin) {
                playerThatWon.add(this.players.get(i));
            }
        }

        // TODO nice
        if (playerThatWon.isEmpty()) {
            int highestGold = 0;
            for (int i = 0; i < this.players.size(); i++) {
                if (this.players.get(i).getGold() > highestGold) {
                    highestGold = this.players.get(i).getGold();
                }
            }

            for (int i = 0; i < this.players.size(); i++) {
                if (this.players.get(i).getGold() == highestGold) {
                    playerThatWon.add(this.players.get(i));
                }
            }
        }

        if (playerThatWon.size() == 1) {
            return String.format("%s has won!", playerThatWon.get(0).toString());
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playerThatWon.size() - 2; i++) {
            sb.append(String.format("%s, ", playerThatWon.get(i).toString()));
        }

        sb.append(String.format("%s and %s have won!",
                playerThatWon.get(playerThatWon.size() - 2).toString(),
                playerThatWon.get(playerThatWon.size() - 1).toString()));

        return sb.toString();
    }

    /**
     * Returns the string representation of the player list.
     * Every player is represented by a line containing the player id, the name and
     * the amount of gold the player has.
     * 
     * @return Player list string representation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.players.size(); i++) {
            final Player currentPlayer = this.players.get(i);
            sb.append(String.format("Player %d (%s): %d", i + 1, currentPlayer.toString(), currentPlayer.getGold()));
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
