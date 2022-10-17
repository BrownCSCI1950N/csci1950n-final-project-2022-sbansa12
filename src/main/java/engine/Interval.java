package engine;

public final class Interval {
    private final double min;
    private final double max;

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Check if two intervals overlap
     * @param other - the interval we will check for if it overlaps with this interval
     * @return - true if the intervals overlap, else false. If the intervals are next to each other, or only they boundaries overlap, return false.
     */
    public boolean overlap(Interval other) {
        return (this.min < other.max) && (other.min < this.max);
    }

    public boolean inInterval(double point) {
        return this.min <= point && point <= this.max;
    }
}
