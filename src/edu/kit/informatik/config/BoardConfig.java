package edu.kit.informatik.config;

/**
 * Class containing all representations for the board
 * 
 * @author uiljo
 * @version 1.0
 */
public final class BoardConfig {

    /** Represents an empty row of a tile */
    public static final String EMPTY_ROW = "|     |";

    /** Represents the barn when a countdown is set */
    public static final String BARN_COUNTDOWN = "| B %d |";
    /** Represents the barn if no countdown is set */
    public static final String BARN_NO_COUNTDOWN = "| B * |";

    /** Represents the planted vegetable from a plantable tile */
    public static final String TILE_PLANTED_VEGETABLE = "|  %s  |";
    /** Represents the amount of planted vegetables on a plantable tile */
    public static final String TILE_CAPACITY = "| %d/%d |";

    public static final String GARDEN = "| G %s |";
    public static final String FIELD = "| Fi %s|";
    public static final String LARGE_FIELD = "|LFi %s|";
    public static final String FOREST = "| Fo %s|";
    public static final String LARGE_FOREST = "|LFo %s|";

    private BoardConfig() {
        throw new AssertionError(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
    }
}
