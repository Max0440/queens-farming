package edu.kit.informatik;

public enum VegetableType {

    CARROT("carrot", "carrots", "C", 1),
    SALAD("salad", "salads", "S", 2),
    TOMATO("tomato", "tomatoes", "T", 3),
    MUSHROOM("mushroom", "mushrooms", "M", 4);

    private final String name;
    private final String plural;
    private final String abbreviation;
    private final int timeToGrow;

    /**
     * Instantiates a new {@link VegetableType}
     * 
     * @param name         lower case representation of enum
     * @param plural       plural case representation of enum
     * @param abbreviation abbreviation of enum
     * @param timeToGrow   time in rounds the plant needs to grow
     */
    private VegetableType(final String name, final String plural, final String abbreviation, final int timeToGrow) {
        this.name = name;
        this.plural = plural;
        this.abbreviation = abbreviation;
        this.timeToGrow = timeToGrow;
    }

    /**
     * Returns the corresponding enum to a string
     * 
     * @param s string to convert to {@link VegetableType}
     * @return vegetable type
     * @throws IllegalArgumentException
     */
    public static VegetableType fromString(final String s) throws IllegalArgumentException {
        for (final VegetableType type : VegetableType.values()) {
            if (type.name.equals(s)) {
                return type;
            }
        }

        throw new IllegalArgumentException();
    }

    /**
     * Returns the plural representation of the enum value
     * 
     * @return the plural representation
     */
    public String getPlural() {
        return this.plural;
    }

    /**
     * Returns the plural representation of the enum value
     * 
     * @return the plural representation
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * Returns the time in round the plant needs to grow till it doubles the amount
     * 
     * @return time to grow
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
        return this.name;
    }
}
