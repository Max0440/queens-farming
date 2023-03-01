package edu.kit.informatik.game;

import java.util.Map;
import java.util.Map.Entry;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.board.Board;
import edu.kit.informatik.game.board.TileStack;
import edu.kit.informatik.game.player.Player;
import edu.kit.informatik.game.player.PlayerList;
import edu.kit.informatik.game.type.PlantableTileType;
import edu.kit.informatik.game.type.VegetableType;

/**
 * Represents the game "queens farming".
 * 
 * @author uiljo
 * @version 1.0
 */
public class QueensFarming {

    private static final int MAX_ACTION_COUNT = 2;

    private boolean isActive;
    private int currentTurn;
    private int remainingActions;

    private final int goldToWin;
    private final Market market;
    private final PlayerList playerList;
    private final TileStack tileStack;

    /**
     * Instantiates a new {@link QueensFarming}.
     * 
     * @param playerNames array with names of all players
     * @param goldAtStart gold at the start of the game
     * @param goldToWin   gold which is needed to win
     * @param seed        seed for shuffling tile stack
     */
    public QueensFarming(final String[] playerNames, final int goldAtStart, final int goldToWin, final long seed) {
        this.isActive = true;
        this.currentTurn = -1;

        this.goldToWin = goldToWin;
        this.market = new Market();
        this.tileStack = new TileStack(playerNames.length, seed);
        this.playerList = new PlayerList(playerNames, goldAtStart);
    }

    private Player getCurrentPlayer() {
        return this.playerList.getCurrentPlayer(currentTurn);
    }

    /**
     * Returns whether or not the game is active.
     * 
     * @return whether or not the game is active.
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Returns whether or not the current turn is running.
     * 
     * @return whether or not the current turn is running.
     */
    public boolean isTurnRunning() {
        return this.remainingActions != 0;
    }

    /**
     * Starts a new turn and return all information of the player in a string.
     * 
     * @return information of current player.
     */
    public String startNextTurn() {
        StringBuilder sb = new StringBuilder();

        // update prices in market
        this.market.startNextTurn();

        // reset values for next turn
        this.currentTurn++;

        // new round
        if (this.currentTurn == this.playerList.size()) {
            this.currentTurn = 0;

            // check if someone won
            boolean someoneWon = this.playerList.someoneWon(this.goldToWin);
            if (someoneWon) {
                this.isActive = false;
                return null;
            }
        }
        this.remainingActions = MAX_ACTION_COUNT;

        sb.append(System.lineSeparator());
        sb.append(String.format("It is %s's turn!", this.getCurrentPlayer().toString()));

        String playerInformation = this.getCurrentPlayer().startNextTurn();
        if (playerInformation != null) {
            sb.append(System.lineSeparator());
            sb.append(playerInformation);
        }

        return sb.toString();
    }

    /**
     * Handles the end of the game.
     * 
     * @return a list of all players and those who have won.
     */
    public String endGame() {
        StringBuilder sb = new StringBuilder();
        String playerThatWon = this.playerList.endGame(this.goldToWin);

        sb.append(this.playerList.toString());
        sb.append(System.lineSeparator());
        sb.append(playerThatWon);

        return sb.toString();
    }

    /**
     * Ends the turn of the current player.
     * 
     * @return {@code null}.
     */
    public String endTurn() {
        this.remainingActions = 0;
        return null;
    }

    /**
     * Returns the {@link Barn} string representation from current player.
     * 
     * @return The barn string representation.
     * @see Player#showBarn()
     */
    public String showBarn() {
        return this.getCurrentPlayer().showBarn();
    }

    /**
     * Returns the {@link Board} string representation from current player.
     * 
     * @return Thr board string representation.
     */
    public String showBoard() {
        return this.getCurrentPlayer().showBoard();
    }

    /**
     * Returns the {@link Market} string representation.
     * 
     * @return The market string representation.
     * @see Market#toString()
     */
    public String showMarket() {
        return this.market.toString();
    }

    /**
     * Sells all vegetables of the current player, add the gold to the player
     * balance.
     * 
     * @return a message telling how many vegetables were sold and for how much
     *         gold.
     * @see #sell(Map)
     */
    public String sellAll() {
        Map<VegetableType, Integer> playersVegetables = this.getCurrentPlayer().getVegetables();
        return this.sell(playersVegetables);
    }

    /**
     * Checks wether the player owns all vegetables in the list.
     * If the player owns all vegetables they are trying to sell, the vegetables
     * will be removed from the player's barn and the sum will be added to the
     * player's balance.
     * 
     * @param vegetablesToSell A map containing with the amount of every vegetable
     *                         to sell.
     * @return a message telling how many vegetables were sold and for what price.
     * @throws GameException when player doesn't own at least one of the vegetables
     *                       they wants to sell.
     */
    public String sell(Map<VegetableType, Integer> vegetablesToSell) throws GameException {
        Map<VegetableType, Integer> playersVegetables = this.getCurrentPlayer().getVegetables();

        // check if player owns all the vegetables they want to sell
        for (Entry<VegetableType, Integer> entry : vegetablesToSell.entrySet()) {
            if (entry.getValue() > playersVegetables.get(entry.getKey())) {
                throw new GameException(ErrorMessages.VEGETABLE_NOT_OWNED);
            }
        }

        // sell all vegetables from list one by one
        int totalPrice = 0;
        int soldCount = 0;
        for (Entry<VegetableType, Integer> entry : vegetablesToSell.entrySet()) {
            VegetableType vegetable = entry.getKey();

            int vegetableCount = vegetablesToSell.get(vegetable);
            for (int i = 0; i < vegetableCount; i++) {
                this.getCurrentPlayer().sell(vegetable);
                totalPrice += this.market.sell(vegetable);
                soldCount++;
            }
        }
        this.getCurrentPlayer().addGold(totalPrice);

        this.remainingActions -= 1;
        if (soldCount == 1) {
            return String.format("You have sold 1 vegetable for %d gold.", totalPrice);
        }
        return String.format("You have sold %d vegetables for %d gold.", soldCount, totalPrice);
    }

    /**
     * Buys a given vegetable and return a message containing the name of the
     * vegetable and the price
     * 
     * @param vegetable to buy
     * @return a message containing the name of the vegetable and the price
     * @throws GameException when the player hasn't enough gold
     */
    public String buyVegetable(VegetableType vegetable) throws GameException {
        final int price = this.market.getPrice(vegetable);

        this.getCurrentPlayer().addGold(price * -1);
        this.getCurrentPlayer().addVegetable(vegetable, 1);

        this.remainingActions -= 1;
        return String.format("You have bought a %s for %d gold.", vegetable.getSingular(), price);
    }

    /**
     * Buys a random plantable tile at a given location on the board.
     * Returns an error message if the player has not enough money to buy the tile.
     * 
     * @param xCoordinate The x-coordinate of the new land
     * @param yCoordinate The y-coordinate of the new land
     * @return a message containing the tile type bought and the price.
     * @throws GameException if there are no more tiles on the tile stack, if the
     *                       tile is not placable on the given location or if the
     *                       player hasn't enough gold
     */
    public String buyLand(int xCoordinate, int yCoordinate) throws GameException {
        final Board playerBoard = this.getCurrentPlayer().getBoard();
        final int price = this.tileStack.calculatePrice(xCoordinate, yCoordinate);

        if (this.tileStack.isEmpty()) {
            throw new GameException(ErrorMessages.TILE_STACK_EMPTY);
        }
        if (!playerBoard.isPlacableSpace(xCoordinate, yCoordinate)) {
            throw new GameException(ErrorMessages.LAND_NOT_PLACABLE);
        }

        this.getCurrentPlayer().addGold(price * -1);

        final PlantableTileType tileType = this.tileStack.drawTile();
        playerBoard.addTile(xCoordinate, yCoordinate, tileType);

        this.remainingActions -= 1;
        return String.format("You have bought a %s for %d gold.", tileType.getName(), price);
    }

    /**
     * Harvests a given vegetable from a given plantable tile.
     * 
     * @param xCoordinate     The x-coordinate of the plantable tile.
     * @param yCoordinate     The y-coordinate of the plantable tile.
     * @param amountToHarvest The amount of vegetables the player wants to harvest.
     * @return a message containing how many vegetables were harvested and what type
     *         of vegetable
     * @throws GameException if the vegetable couldn't be harvested.
     * @see Player#harvest(int, int, int)
     */
    public String harvest(int xCoordinate, int yCoordinate, int amountToHarvest) throws GameException {
        final VegetableType harvestedVegetable = this.getCurrentPlayer().harvest(xCoordinate, yCoordinate,
                amountToHarvest);

        this.remainingActions -= 1;
        if (amountToHarvest > 1) {
            return String.format("You have harvested %d %s.", amountToHarvest, harvestedVegetable.getPlural());
        }
        return String.format("You have harvested %d %s.", amountToHarvest, harvestedVegetable.getSingular());
    }

    /**
     * Plants a given vegetable at a given location.
     * 
     * @param xCoordinate The x-coordinate of the plantable tile.
     * @param yCoordinate The y-coordinate of the plantable tile.
     * @param vegetable   The type of vegetable to plant.
     * @return {@code null}
     * @throws GameException when the vegetable is not in the barn, the field
     *                       doesn't exists or the vegetable can't be planted on the
     *                       field type
     */
    public String plant(int xCoordinate, int yCoordinate, VegetableType vegetable) throws GameException {
        this.getCurrentPlayer().plant(xCoordinate, yCoordinate, vegetable);

        this.remainingActions -= 1;
        return null;
    }

    /**
     * Quits the game.
     */
    public void quit() {
        this.remainingActions = 0;
        this.isActive = false;
    }
}
