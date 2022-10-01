package engine;

public final class Interval {
    private final double min;
    private final double max;

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean overlap(Interval other) {
        return (this.min <= other.max) && (other.min <= this.max);
    }

    public boolean inInterval(double point) {
        return this.min <= point && point <= this.max;
    }
}
