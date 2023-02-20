package edu.kit.informatik.type;

public enum VegetableType {

    CARROT("carrot", "carrots", "C", 1),
    SALAD("salad", "salads", "S", 2),
    TOMATO("tomato", "tomatoes", "T", 3),
    MUSHROOM("mushroom", "mushrooms", "M", 4);

    private final String singular;
    private final String plural;
    private final String abbreviation;
    private final int timeToGrow;

    /**
     * Instantiates a new {@link VegetableType}
     * 
     * @param singular     lower case representation of enum
     * @param plural       plural case representation of enum
     * @param abbreviation abbreviation of enum
     * @param timeToGrow   time in rounds the plant needs to grow
     */
    private VegetableType(final String singular, final String plural, final String abbreviation, final int timeToGrow) {
        this.singular = singular;
        this.plural = plural;
        this.abbreviation = abbreviation;
        this.timeToGrow = timeToGrow;
    }

    /**
     * Returns the corresponding enum to a string
     * 
     * @param s string to convert to {@link VegetableType}
     * @return vegetable type
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
     * Returns the plural representation of the enum value
     * 
     * @return the plural representation
     */
    public String getPlural() {
        return this.plural;
    }

    /**
     * Returns the singular representation of the enum value
     * 
     * @return the singular representation
     */
    public String getSingular() {
        return this.singular;
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
        return this.singular;
    }
}
