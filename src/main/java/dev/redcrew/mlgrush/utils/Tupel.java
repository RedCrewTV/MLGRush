package dev.redcrew.mlgrush.utils;

/**
 * This file is a JavaDoc!
 * Created: 11/1/2024
 * <p>
 * Belongs to MLGRush
 * <p>
 *
 * @author RedCrew <p>
 * Discord: redcrew <p>
 * Website: <a href="https://redcrew.dev/">https://redcrew.dev/</a>
 */
public final class Tupel<T, V> {

    private T first;
    private V last;

    public Tupel(T first, V last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public V getLast() {
        return last;
    }

    public void setLast(V last) {
        this.last = last;
    }
}
