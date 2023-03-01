package edu.kit.informatik.config;

/**
 * Class containing all representations for the board
 * 
 * @author uiljo
 * @version 1.0
 */
public final class BoardConfig {
    /**
     * Represents a star used to indicate that no countdown is running or nothing is
     * planted
     */
    public static final String STAR = "*";

    /** Represents an empty row of a tile */
    public static final String EMPTY_ROW = "|     |";

    /** Represents the barn with remaining turns until all vegetables spoil */
    public static final String BARN_COUNTDOWN = "| B %s |";

    /** Represents the planted vegetable from a plantable tile */
    public static final String TILE_PLANTED_VEGETABLE = "|  %s  |";
    /** Represents the amount of planted vegetables on a plantable tile */
    public static final String TILE_CAPACITY = "| %d/%d |";

    /** Represents a garden on the board */
    public static final String GARDEN = "| G %s |";
    /** Represents a field on the board */
    public static final String FIELD = "| Fi %s|";
    /** Represents a large field on the board */
    public static final String LARGE_FIELD = "|LFi %s|";
    /** Represents a forest on the board */
    public static final String FOREST = "| Fo %s|";
    /** Represents a large forest on the board */
    public static final String LARGE_FOREST = "|LFo %s|";

    private BoardConfig() {
        throw new AssertionError(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
    }
}
