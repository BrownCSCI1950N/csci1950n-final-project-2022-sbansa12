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

    public Double mtv(Interval b) {
        Interval a = this;
        double aRight = b.max - a.min;
        double aLeft = a.max - b.min;
        if (aLeft < 0 || aRight < 0) {
            return null;
        }
        if (aRight < aLeft) {
            return aRight;
        } else {
            return -aLeft;
        }
    }

    @Override
    public String toString() {
        return "Interval: (" + min + ", " + max + ")";
    }
}
