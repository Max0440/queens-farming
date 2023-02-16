package edu.kit.informatik.player;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a list of all {@link Player} of the game queens farming.
 * 
 * @author uiljo
 * @version 1.0
 */
public class PlayerList {

    private List<Player> players = new ArrayList<>();

    /**
     * Instantiates a new {@link PlayerList}.
     * 
     * @param playerNames array with all player names
     * @param goldAtStart the amount of gold, the players have at the beginning
     */
    public PlayerList(String[] playerNames, int goldAtStart) {
        for (String playerName : playerNames) {
            this.players.add(new Player(playerName, goldAtStart));
        }
    }

    /**
     * Returns amount of players in the game
     * 
     * @return size of player list
     */
    public int size() {
        return players.size();
    }

    /**
     * Returns the current player based on the current turn
     * 
     * @param currentTurn of the round
     * @return the player whose turn it is
     */
    public Player getCurrentPlayer(int currentTurn) {
        return players.get(currentTurn);
    }

    public String getPlayerThatWon(int goldToWin) {
        List<Player> playerThatWon = new ArrayList<>();

        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getGold() >= goldToWin) {
                playerThatWon.add(this.players.get(i));
            }
        }

        if (playerThatWon.isEmpty()) {
            return null;
        }

        if (playerThatWon.size() == 1) {
            return String.format("%s has won! ", playerThatWon.get(0).getName());
        }

        // TODO? Reigenfolge wichtig? Gleich wie bei playerList.toString() oder egal
        StringBuilder sb = new StringBuilder();
        for (int i = playerThatWon.size() - 1; i > 1; i--) {
            sb.append(String.format("%s, ", playerThatWon.get(i).getName()));
        }

        sb.append(String.format("%s and %s have won! ",
                playerThatWon.get(0).getName(),
                playerThatWon.get(1).getName()));

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.players.size(); i++) {
            sb.append(String.format("Player %d ", i + 1));
            sb.append(this.players.get(i));

            if (i < this.players.size() - 1) {
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
