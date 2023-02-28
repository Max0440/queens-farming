package edu.kit.informatik.game.player;

import java.util.Map;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.board.Barn;
import edu.kit.informatik.game.board.Board;
import edu.kit.informatik.game.type.VegetableType;

/**
 * Represents a player in queens farming.
 * 
 * @author uiljo
 * @version 1.0
 */
public class Player {

    private int gold;
    private final String name;
    private final Board board;

    /**
     * Instantiates a new {@link Player}.
     * 
     * @param name The name of the player
     * @param gold The amount of gold, the player has at the beginning.
     */
    public Player(final String name, final int gold) {
        this.gold = gold;
        this.name = name;

        this.board = new Board();
    }

    /**
     * Returns the current amount of gold the player has.
     * 
     * @return gold of player.
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Returns the object of the barn form player.
     * 
     * @return barn from player
     */
    public Barn getBarn() {
        return this.board.getBarn();
    }

    /**
     * Returns the object of the board form player
     * 
     * @return board from player
     */
    public Board getBoard() {
        return this.board;
    }

    public Map<VegetableType, Integer> getVegetables() {
        return this.getBarn().getVegetables();
    }

    /**
     * Adds a given amount of a given vegetable to the player's barn.
     * 
     * @param vegetable The type of vegetable to be added
     * @param amount    The amount of vegetables to be added
     * @see Barn#addVegetable(VegetableType)
     */
    public void addVegetable(final VegetableType vegetable, final int amount) {
        for (int i = 0; i < amount; i++) {
            this.getBarn().addVegetable(vegetable);
        }
    }

    private void removeVegetable(final VegetableType vegetable) throws GameException {
        this.getBarn().removeVegetable(vegetable);
    }

    /**
     * Adds a given amount of gold to the player's balance. If the resulting amount
     * would be negative an GameException is thrown.
     * 
     * @param amount The amount of gold to add to the player's balance (negative to
     *               remove gold).
     * @throws GameException when balance would be negative.
     */
    public void addGold(final int amount) throws GameException {
        if (this.gold + amount < 0) {
            throw new GameException(ErrorMessages.NOT_ENOUGH_GOLD);
        }
        this.gold += amount;
    }

    /**
     * Removes a given vegetable.
     * 
     * @param vegetable Type of vegetable to sell.
     * @see Barn#removeVegetable(VegetableType)
     */
    public void sell(final VegetableType vegetable) {
        this.getBarn().removeVegetable(vegetable);
    }

    /**
     * Harvests a given amount of vegetables from the given tile and adds them to
     * the player's barn
     * Harvests a given vegetable from a given filed by reducing the number of
     * planted vegetables by the amount, the player wants to harvest
     * 
     * @param xCoordinate     The x-coordinate of the tile to be harvested.
     * @param yCoordinate     The y-coordinate of the tile to be harvested.
     * @param amountToHarvest The amount of vegetables to be harvested.
     * @return The type of the harvested vegetable.
     * @throws GameException when the tile doesn't exists or the amount couldn't be
     *                       harvested.
     * @see Board#harvest(int, int, int)
     * @see #addVegetable(VegetableType, int)
     */
    public VegetableType harvest(final int xCoordinate, final int yCoordinate, final int amountToHarvest)
            throws GameException {
        final VegetableType harvestedVegetable = this.getBoard().harvest(xCoordinate, yCoordinate, amountToHarvest);
        this.addVegetable(harvestedVegetable, amountToHarvest);
        return harvestedVegetable;
    }

    /**
     * Plants a given vegetable on a given tile and removes the vegetable from the
     * player's barn.
     * 
     * @param xCoordinate The x-coordinate of the field to be planted.
     * @param yCoordinate The y-coordinate of the field to be planted.
     * @param vegetable   The type of vegetable to be planted
     * @throws GameException when the player doesn't own the vegetable, the field
     *                       doesn't exists or the vegetable can't be planted on
     *                       the field type
     */
    public void plant(final int xCoordinate, final int yCoordinate, final VegetableType vegetable)
            throws GameException {
        if (!this.getBarn().hasInBarn(vegetable)) {
            throw new GameException(ErrorMessages.VEGETABLE_NOT_OWNED);
        }

        this.getBoard().plant(xCoordinate, yCoordinate, vegetable);
        this.removeVegetable(vegetable);
    }

    /**
     * Handles a turn in the game.
     * 
     * @return a message from the game board or {@code null}
     * @see Board#startNextTurn()
     */
    public String startNextTurn() {
        return this.getBoard().startNextTurn();
    }

    public String showBarn() {
        return this.getBarn().toStringFormatted(this.gold);
    }

    /**
     * Returns the string representation of the player which simply is the name of
     * the player.
     * 
     * @return The player's name.
     */
    @Override
    public String toString() {
        return this.name;
    }
}