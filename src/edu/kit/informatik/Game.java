package edu.kit.informatik;

import java.util.List;

import edu.kit.informatik.player.Player;
import edu.kit.informatik.player.PlayerList;
import edu.kit.informatik.tiles.PlantableTileType;
import edu.kit.informatik.tiles.TileStack;

public class Game {

    private static final int MAX_ACTION_COUNT = 2;

    private int currentTurn;
    private int remainingActions;

    private int goldToWin;
    private boolean isActive;
    private Market market;
    private PlayerList playerList;
    private TileStack tileStack;

    public Game(String[] playerNames, int goldAtStart, int goldToWin, long seed) {
        this.goldToWin = goldToWin;
        this.isActive = true;
        this.market = new Market();
        this.tileStack = new TileStack(playerNames.length, seed);

        this.currentTurn = -1;

        this.playerList = new PlayerList(playerNames, goldAtStart);
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isTurnRunning() {
        return this.remainingActions != 0;
    }

    /**
     * Checks which players have won and returns them in a list
     * 
     * @return players that won
     */
    // public List<Player> getPlayerThatWon() {
    // return this.playerList.getPlayerThatWon(this.goldToWin);
    // }

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
            String yeet = this.playerList.getPlayerThatWon(this.goldToWin);
            if (yeet != null) {
                this.isActive = false;

                sb.append(this.playerList.toString());
                sb.append(System.lineSeparator());
                sb.append(yeet);
                return sb.toString();
            }
            // if (!this.getPlayerThatWon().isEmpty()) {
            // this.isActive = false;

            // StringBuilder sb = new StringBuilder();
            // sb.append(this.playerList.toString());
            // sb.append(System.lineSeparator());
            // sb.append("TODO who has won");
            // return sb.toString();
            // }
        }
        this.remainingActions = MAX_ACTION_COUNT;

        sb.append(System.lineSeparator());
        sb.append(String.format("It is %s's turn!", this.getCurrentPlayer().getName()));

        String playerInformation = this.getCurrentPlayer().startNextTurn();
        if (playerInformation != null) {
            sb.append(System.lineSeparator());
            sb.append(playerInformation);
        }

        return sb.toString();
    }

    private Player getCurrentPlayer() {
        return this.playerList.getCurrentPlayer(currentTurn);
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
        // TODO print
        StringBuilder sb = new StringBuilder();

        sb.append(this.getCurrentPlayer().getBarn().toString());
        sb.append(System.lineSeparator());
        sb.append(this.getCurrentPlayer().getGold());

        return sb.toString();
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
     * @return a string telling how many vegetables were sold and for how much gold
     */
    public String sellAll() {
        return null;
    }

    public String sell(List<VegetableType> vegetables) {
        int totalPrice = 0;
        for (int i = 0; i < vegetables.size(); i++) {
            try {
                this.getCurrentPlayer().sell(vegetables.get(i));
            } catch (IllegalArgumentException e) {
                return null;
                // TODO? Was machen, wenn ein Gemüse nicht im Besitz ist
            }
            totalPrice += this.market.sell(vegetables.get(i));
        }
        this.getCurrentPlayer().addGold(totalPrice);

        this.remainingActions -= 1;

        if (vegetables.size() == 1) {
            return String.format("You have sold 1 vegetable for %d gold.", totalPrice);
        } else {
            return String.format("You have sold %d vegetables for %d gold.", vegetables.size(), totalPrice);
        }

    }

    /**
     * Buys a given vegetable and return a message containing the name of the
     * vegetable and the price
     * 
     * @param vegetable to buy
     * @return a message containing the name of the vegetable and the price
     */
    public String buyVegetable(VegetableType vegetable) {
        int price = this.market.getPrice(vegetable);

        try {
            this.getCurrentPlayer().addGold(price * -1);
        } catch (IllegalArgumentException e) {
            return Config.ERROR_NOT_ENOUGH_GOLD;
        }

        this.getCurrentPlayer().buy(vegetable);
        this.remainingActions -= 1;

        return String.format("You have bought a %s for %d gold.", vegetable.toString(), price);
    }

    /**
     * Buys a random plantable tile at a given position on the board.
     * Returns an error message if the player has not enough money to buy the tile.
     * 
     * @param xCoordinate
     * @param yCoordinate
     * @return
     */
    public String buyLand(int xCoordinate, int yCoordinate) {
        Board playerBoard = this.getCurrentPlayer().getBoard();
        int price = playerBoard.calculatePrice(xCoordinate, yCoordinate);

        if (!playerBoard.isPlacableSpace(xCoordinate, yCoordinate)) {
            return Config.ERROR_LAND_NOT_PLACABLE;
        }

        try {
            this.getCurrentPlayer().addGold(price * -1);
        } catch (IllegalArgumentException e) {
            return Config.ERROR_NOT_ENOUGH_GOLD;
        }

        PlantableTileType tileType = this.tileStack.drawTile();
        playerBoard.addTile(xCoordinate, yCoordinate, tileType);

        return String.format("You have bought a %s for %d gold.", tileType.getName(), price);
    }

    public String plant(int xCoordinate, int yCoordinate, VegetableType vegetable) {
        // TODO: handle exception
        if (!this.getCurrentPlayer().getBarn().hasInBarn(vegetable)) {
            return "Error: not in barn";
        }

        try {
            this.getCurrentPlayer().getBoard().plant(xCoordinate, yCoordinate, vegetable);
        } catch (IllegalArgumentException e) {
            return "Error: not plantable";
        }
        return "You have palnted";
    }

    // TODO GANZ WICHTIG ACTIONS ÜBERALL RUNTER ZÄHLEN

    /**
     * quits the game
     */
    public void quit() {
        this.remainingActions = 0;
        this.isActive = false;
    }
}
