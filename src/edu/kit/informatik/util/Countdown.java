package edu.kit.informatik.util;

/**
 * Represents a countdown that can count down to a specified value.
 * 
 * @author uiljo
 * @version 1.0
 */
public class Countdown {

    private boolean isActive;
    private int value;
    private int minValue;

    /**
     * Instantiates a new {@link Countdown} with the initial value and the minimum
     * value set to 0.
     */
    public Countdown() {
        this(0, 0, false);
    }

    /**
     * Instantiates a new {@link Countdown} with a given initial and minimum value.
     * 
     * @param value    The initial value of the countdown.
     * @param minValue The minimum value ot the countdown.
     * @param isActive Whether the countdown should be active or not.
     */
    public Countdown(final int value, final int minValue, final boolean isActive) {
        this.value = value;
        this.minValue = minValue;
        this.isActive = isActive;
    }

    /**
     * Sets the active status of the countdown.
     * 
     * @param isActive A boolean whether the countdown is active or not.
     */
    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Sets the minimum value of the countdown.
     * 
     * @param minValue The new minimum value of the countdown.
     */
    public void setMinValue(final int minValue) {
        this.minValue = minValue;
    }

    /**
     * Sets the current value of the countdown.
     * 
     * @param value The new current value of the countdown.
     */
    public void setValue(final int value) {
        this.value = value;
    }

    /**
     * Checks whether the countdown is active or not.
     * 
     * @return {@code true} if the countdown is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Returns the current value of the countdown.
     * 
     * @return The current value of the countdown.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Decrements the value of the countdown by one and returns weather the
     * countdown has reached its minimum value.
     * 
     * @return {@code true} if the countdown has reached its minimum value,
     *         {@code false} otherwise.
     */
    public boolean nextStep() {
        this.value -= 1;

        return this.value <= this.minValue;
    }
}
