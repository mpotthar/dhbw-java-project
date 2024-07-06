package com.example.utility;

import java.util.Objects;

/**
 * Represents a coordinate with two values, X and Y.
 *
 * @param <X> the type of the X value
 * @param <Y> the type of the Y value
 */
public class Coordinate<X, Y> {
    public final X x;
    public final Y y;

    /**
     * Constructs a new Coordinate object with the specified X and Y values.
     *
     * @param x the X value
     * @param y the Y value
     */
    public Coordinate(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if this Coordinate is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coordinate<?, ?> that = (Coordinate<?, ?>) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    /**
     * Returns the hash code value for this Coordinate.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
