package edu.kit.informatik.player;

import edu.kit.informatik.Board;
import edu.kit.informatik.VegetableType;
import edu.kit.informatik.tiles.Barn;

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
    public void buy(VegetableType vegetable) {
        this.getBarn().addVegetable(vegetable);
    }

    /**
     * Handle sell action. Reduces the amount of the given vegetable in the barn. If
     * the player doesn't have the given vegetable it throws an
     * IllegalArgumentException
     * 
     * @param vegetable to sell
     * @throws IllegalArgumentException when user doesn't have the vegetable in barn
     */
    public void sell(VegetableType vegetable) throws IllegalArgumentException {
        this.getBarn().removeVegetable(vegetable);
    }

    /**
     * Adds the given amount of gold to the balance of the player. If the resulting
     * amount is negative it throws an IllegalArgumentException
     * 
     * @param amount of gold to add (remove)
     * @throws IllegalArgumentException when balance is below zero
     */
    public void addGold(int amount) throws IllegalArgumentException {
        if (this.gold + amount < 0) {
            throw new IllegalArgumentException();
        }
        this.gold += amount;
    }

    public String startNextTurn() {
        // TODO what has grown
        // return this.getBarn().startNextTurn();
        return this.getBoard().startNextTurn();
    }

    @Override
    public String toString() {
        return String.format("(%s): %d", this.getName(), this.getGold());
    }
}
