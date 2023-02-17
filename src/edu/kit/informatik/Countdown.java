package edu.kit.informatik;

public class Countdown {

    private boolean isActive;
    private int value;
    private int minValue;

    public Countdown() {
        this(0, 0, false);
    }

    public Countdown(final int value, final int minValue) {
        this(value, minValue, false);
    }

    public Countdown(final int value, final int minValue, final boolean isActive) {
        this.value = value;
        this.minValue = minValue;
        this.isActive = isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public int getValue() {
        return this.value;
    }

    public boolean nextStep(int delta) {
        this.value += delta;

        return this.value <= this.minValue;
    }
}
