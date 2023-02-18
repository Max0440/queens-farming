package edu.kit.informatik.game.player;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.Board;
import edu.kit.informatik.game.GameException;
import edu.kit.informatik.game.VegetableType;
import edu.kit.informatik.game.tiles.Barn;

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
     * Returns the name the player should be addressed with
     * 
     * @return name of player
     */
    public String getName() {
        return this.name;
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

    public void removeVegetable(VegetableType vegetable) throws GameException {
        this.getBarn().removeVegetable(vegetable);
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

    public VegetableType harvest(int xCoordinate, int yCoordinate, int count) throws GameException {
        final VegetableType harvestedVegetable = this.getBoard().harvest(xCoordinate, yCoordinate, count);
        this.addVegetable(harvestedVegetable, count);
        return harvestedVegetable;
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

    public String startNextTurn() {
        return this.getBoard().startNextTurn();
    }

    @Override
    public String toString() {
        return String.format("(%s): %d", this.getName(), this.getGold());
    }
}