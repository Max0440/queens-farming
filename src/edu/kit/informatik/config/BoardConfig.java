package edu.kit.informatik.config;

public class BoardConfig {
    public static final String EMPTY_ROW = "|     |";

    public static final String BARN_COUNTDOWN = "| B %d |";
    public static final String BARN_NO_COUNTDOWN = "| B * |";

    public static final String TILE_PLANTED_VEGETABLE = "|  %s  |";
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
