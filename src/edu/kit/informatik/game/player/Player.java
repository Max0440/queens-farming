package edu.kit.informatik.game.player;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.board.Barn;
import edu.kit.informatik.game.board.Board;
import edu.kit.informatik.type.VegetableType;

/**
 * Represents a player in queens farming.
 * 
 * @author uiljo
 * @version 1.0
 */
public class Player {

    private int gold;
    private String name;
    private Board board;

    /**
     * Instantiates a new {@link Player}
     * 
     * @param name name of the player
     * @param gold the amount of gold, the player has at the beginning
     */
    public Player(String name, int gold) {
        this.gold = gold;
        this.name = name;

        this.board = new Board();
    }

    /**
     * Returns the amount of gold the player has
     * 
     * @return gold of play
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Returns the object of the barn form player
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

    /**
     * Adds the given vegetable to the players barn
     * 
     * @param vegetable to add to barn
     */
    public void addVegetable(VegetableType vegetable, int amount) {
        for (int i = 0; i < amount; i++) {
            this.getBarn().addVegetable(vegetable);
        }
    }

    private void removeVegetable(VegetableType vegetable) throws GameException {
        this.getBarn().removeVegetable(vegetable);
    }

    /**
     * Adds the given amount of gold to the balance of the player. If the resulting
     * amount is negative it throws an GameException
     * 
     * @param amount of gold to add (remove)
     * @throws GameException when balance is below zero
     */
    public void addGold(int amount) throws GameException {
        if (this.gold + amount < 0) {
            throw new GameException(ErrorMessages.NOT_ENOUGH_GOLD);
        }
        this.gold += amount;
    }

    public boolean hasInBarn(VegetableType vegetable) {
        return this.getBarn().hasInBarn(vegetable);
    }

    /**
     * Handle sell action. Reduces the amount of the given vegetable in the barn. If
     * the player doesn't have the given vegetable it throws an GameException
     * 
     * @param vegetable to sell
     * @throws GameException when user doesn't have the vegetable in barn
     */
    public void sell(VegetableType vegetable) throws GameException {
        this.getBarn().removeVegetable(vegetable);
    }

    /**
     * Harvests a given vegetable from a given filed by reducing the number of
     * planted vegetables by the amount, the player wants to harvest
     * 
     * @param xCoordinate     x-coordinate of the field
     * @param yCoordinate     y-coordinate of the field
     * @param amountToHarvest the amount the player wants to harvest
     * @return the harvested {@link VegetableType}
     * @throws GameException TODO
     */
    public VegetableType harvest(int xCoordinate, int yCoordinate, int amountToHarvest) throws GameException {
        final VegetableType harvestedVegetable = this.getBoard().harvest(xCoordinate, yCoordinate, amountToHarvest);
        this.addVegetable(harvestedVegetable, amountToHarvest);
        return harvestedVegetable;
    }

    /**
     * Plants a given vegetable on a field if there is nothing planted yet
     * 
     * @param xCoordinate x-coordinate of the field
     * @param yCoordinate y-coordinate of the field
     * @param vegetable   vegetable to plant
     * @throws GameException when the vegetable is not in the barn, the field
     *                       doesn't exists or the vegetable can't be planted on
     *                       the field type
     */
    public void plant(int xCoordinate, int yCoordinate, VegetableType vegetable) throws GameException {
        if (!this.hasInBarn(vegetable)) {
            throw new GameException(ErrorMessages.VEGETABLE_NOT_OWNED);
        }

        this.getBoard().plant(xCoordinate, yCoordinate, vegetable);
        this.removeVegetable(vegetable);
    }

    public String startNextTurn() {
        return this.getBoard().startNextTurn();
    }

    public String showBarn() {
        // TODO print
        StringBuilder sb = new StringBuilder();

        sb.append(this.getBarn().toString());
        sb.append(System.lineSeparator());
        sb.append(this.getGold());
        // sb.append("TODO");
        return sb.toString();
    }

    /**
     * Returns the string representation of the player which simply is the name of
     * the player
     * 
     * @return player name
     */
    @Override
    public String toString() {
        return this.name;
    }
}