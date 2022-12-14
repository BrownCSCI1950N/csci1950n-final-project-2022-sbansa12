package Pair;

import java.util.Objects;

/**
 * Class to hold two objects of different types together.
 * @param <A> - a type variable
 * @param <B> - a type variable
 */
public class Pair<A, B> {

    private final A left;
    private final B right;

    /**
     * Constructor.
     * @param a - object of type A.
     * @param b - object of type B.
     */
    public Pair(A a, B b) {
        left = a;
        right = b;
    }

    @Override
    public String toString() {
        return "(" + left + " , " + right + ")";
    }

    /**
     * Getter of what is stored at the left of the pair.
     * @return - what is stored at the left of the pair.
     */
    public A getLeft() {
        return left;
    }

    /**
     * Getter of what is stored at the right of the pair.
     * @return - what is stored at the right of the pair.
     */
    public B getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?>) {
            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return pair.left.equals(this.left)
                    && pair.right.equals(this.right);
        } else {
            return false;
        }
    }
}