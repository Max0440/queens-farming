package edu.kit.informatik.game.type;

/**
 * Enum of the types of vegetables in the game
 */
public enum VegetableType {

    /**
     * Carrot vegetable
     */
    CARROT("carrot", "carrots", "C", 1),

    /**
     * Mushroom vegetable
     */
    MUSHROOM("mushroom", "mushrooms", "M", 4),

    /**
     * Salad vegetable
     */
    SALAD("salad", "salads", "S", 2),

    /**
     * Tomato vegetable
     */
    TOMATO("tomato", "tomatoes", "T", 3);

    private final String singular;
    private final String plural;
    private final String abbreviation;
    private final int timeToGrow;

    private VegetableType(final String singular, final String plural, final String abbreviation, final int timeToGrow) {
        this.singular = singular;
        this.plural = plural;
        this.abbreviation = abbreviation;
        this.timeToGrow = timeToGrow;
    }

    /**
     * Returns the corresponding vegetable type by the name.
     * 
     * @param s The string to convert to {@link VegetableType}.
     * @return The corresponding vegetable type or {@code null} if there is non.
     */
    public static VegetableType fromString(final String s) {
        for (final VegetableType type : VegetableType.values()) {
            if (type.getSingular().equals(s)) {
                return type;
            }
        }

        return null;
    }

    /**
     * Returns the plural representation of the vegetable.
     * 
     * @return The plural representation.
     */
    public String getPlural() {
        return this.plural;
    }

    /**
     * Returns the singular representation of the vegetable.
     * 
     * @return The singular representation.
     */
    public String getSingular() {
        return this.singular;
    }

    /**
     * Returns the abbreviation of the vegetable.
     * 
     * @return The abbreviation.
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * Returns the time in rounds the vegetable needs to grow till it duplicates
     * itself.
     * 
     * @return The time needed to grow.
     */
    public int getTimeToGrow() {
        return this.timeToGrow;
    }

    /**
     * Returns the text representation of the enum value
     * 
     * @return the text representation
     */
    @Override
    public String toString() {
        return this.singular;
    }
}