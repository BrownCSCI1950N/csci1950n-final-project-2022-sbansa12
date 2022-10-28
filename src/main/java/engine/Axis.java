package engine;

public enum Axis {
    X,
    Y;

    public Axis flipAxisXY() {
        if (this == X) {
            return Y;
        } else {
            return X;
        }
    }
}
