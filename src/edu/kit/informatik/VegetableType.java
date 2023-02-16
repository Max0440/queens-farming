package edu.kit.informatik;

public enum VegetableType {
    CARROT("carrot", "carrots", "C", 1),
    SALAD("salad", "salads", "S", 2),
    TOMATO("tomato", "tomatoes", "T", 3),
    MUSHROOM("mushroom", "mushrooms", "M", 4);

    private String lowerCase;
    private String plural;
    private String abbreviation;
    private int timeToGrow;

    /**
     * Instantiates a new {@link VegetableType}.
     * 
     * @param lowerCase    lower case representation of enum
     * @param plural       plural case representation of enum
     * @param abbreviation abbreviation of enum
     * @param timeToGrow   time in rounds the plant needs to grow
     */
    private VegetableType(String lowerCase, String plural, String abbreviation, int timeToGrow) {
        this.lowerCase = lowerCase;
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
    public static VegetableType fromString(String s) throws IllegalArgumentException {
        for (VegetableType type : VegetableType.values()) {
            if (type.lowerCase.equals(s)) {
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
        return this.lowerCase;
    }
}
