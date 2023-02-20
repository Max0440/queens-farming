package edu.kit.informatik.game;

import java.util.List;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.player.Player;
import edu.kit.informatik.game.player.PlayerList;
import edu.kit.informatik.game.tiles.TileStack;
import edu.kit.informatik.type.PlantableTileType;
import edu.kit.informatik.type.VegetableType;

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
     * Returns whether or not the game is active
     * 
     * @return whether or not the game is active
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Returns whether or not the current turn is running
     * 
     * @return whether or not the current turn is running
     */
    public boolean isTurnRunning() {
        return this.remainingActions != 0;
    }

    /**
     * Starts a new turn and return all information of the player in a string
     * 
     * @return information of current player
     */
    public String startNextTurn() {
        StringBuilder sb = new StringBuilder();

        // update prices in market
        this.market.startNextTurn();

        // reset values for next turn
        this.currentTurn += 1;

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

    public String endGame() {
        StringBuilder sb = new StringBuilder();
        String playerThatWon = this.playerList.endGame(this.goldToWin);

        sb.append(this.playerList.toString());
        sb.append(System.lineSeparator());
        sb.append(playerThatWon);

        return sb.toString();
    }

    /**
     * Ends the turn of the current player
     * 
     * @return {@code null}
     */
    public String endTurn() {
        this.remainingActions = 0;
        return null;
    }

    /**
     * Returns the {@link Barn} string representation from current player
     * 
     * @return barn string representation
     */
    public String showBarn() {
        return this.getCurrentPlayer().showBarn();
    }

    /**
     * Returns the {@link Board} string representation from current player
     * 
     * @return board string representation
     */
    public String showBoard() {
        return this.getCurrentPlayer().getBoard().toString();
    }

    /**
     * Returns the {@link Market} string representation
     * 
     * @return market string representation
     */
    public String showMarket() {
        return this.market.toString();
    }

    // TODO implement sellAll
    /**
     * Sells all vegetables of the current player, add the gold to the player
     * balance
     * and return a message
     * 
     * @return a message telling how many vegetables were sold and for how much gold
     */
    public String sellAll() {
        return null;
    }

    /**
     * Sells all vegetables in the list and adds the gold to the players balance for
     * all vegetables that could sold. Skips all vegetables, the player doesn't own
     * 
     * @param vegetables list of vegetables to sell
     * @return a message telling how much vegetable was sold and for what price
     */
    public String sell(List<VegetableType> vegetables) {
        int totalPrice = 0;
        int soldCount = 0;
        for (int i = 0; i < vegetables.size(); i++) {
            try {
                this.getCurrentPlayer().sell(vegetables.get(i));
            } catch (GameException e) {
                // skip if player hasn't vegetable he wants to sell
                continue;
            }
            totalPrice += this.market.sell(vegetables.get(i));
            soldCount++;
        }
        this.getCurrentPlayer().addGold(totalPrice);

        this.remainingActions -= 1;
        if (soldCount == 1) {
            return String.format("You have sold 1 vegetable for %d gold.", totalPrice);
        } else {
            return String.format("You have sold %d vegetables for %d gold.", soldCount, totalPrice);
        }

    }

    /**
     * Buys a given vegetable and return a message containing the name of the
     * vegetable and the price
     * 
     * @param vegetable to buy
     * @return a message containing the name of the vegetable and the price
     * @throws GameException if the player hasn't enough gold
     */
    public String buyVegetable(VegetableType vegetable) throws GameException {
        final int price = this.market.getPrice(vegetable);

        this.getCurrentPlayer().addGold(price * -1);
        this.getCurrentPlayer().addVegetable(vegetable, 1);

        this.remainingActions -= 1;
        return String.format("You have bought a %s for %d gold.", vegetable.getSingular(), price);
    }

    /**
     * Buys a random plantable tile at a given position on the board.
     * Returns an error message if the player has not enough money to buy the tile.
     * 
     * @param xCoordinate x-coordinate of new land
     * @param yCoordinate y-coordinate of new land
     * @return a message containing the tile type bought and the price
     * @throws GameException if there are no more tiles on the tile stack, if the
     *                       tile is not placable on the given location or if the
     *                       player hasn't enough gold
     */
    public String buyLand(int xCoordinate, int yCoordinate) throws GameException {
        final Board playerBoard = this.getCurrentPlayer().getBoard();
        final int price = playerBoard.calculatePrice(xCoordinate, yCoordinate);

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
     * Harvests a given vegetable from a given filed
     * 
     * @param xCoordinate     x-coordinate of the field
     * @param yCoordinate     y-coordinate of the field
     * @param amountToHarvest the amount the player wants to harvest
     * @return a message containing how many vegetables were harvested and what type
     *         of vegetable
     * @throws GameException
     */
    public String harvest(int xCoordinate, int yCoordinate, int amountToHarvest) throws GameException {
        final VegetableType harvestedVegetable = this.getCurrentPlayer().harvest(xCoordinate, yCoordinate,
                amountToHarvest);

        this.remainingActions -= 1;
        if (amountToHarvest > 1) {
            return String.format("You have harvested %d %s", amountToHarvest, harvestedVegetable.getPlural());
        }
        return String.format("You have harvested %d %s", amountToHarvest, harvestedVegetable.getSingular());
    }

    /**
     * Plants a given vegetable on a field
     * 
     * @param xCoordinate x-coordinate of the field
     * @param yCoordinate y-coordinate of the field
     * @param vegetable   vegetable to plant
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
     * quits the game
     */
    public void quit() {
        this.remainingActions = 0;
        this.isActive = false;
    }
}
