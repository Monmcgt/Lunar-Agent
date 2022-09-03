package me.monmcgt.code.helpers;

import java.util.Arrays;

public class BufferedHelper <T> {
    private T[] buffer;

    public BufferedHelper(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.buffer = (T[]) new Object[size];
        Arrays.fill(this.buffer, null);
    }

    public void swap(T next) {
        if (buffer.length - 1 >= 0) {
            System.arraycopy(buffer, 1, buffer, 0, buffer.length - 1);
        }
        buffer[buffer.length - 1] = next;
    }

    public void clear() {
        Arrays.fill(buffer, null);
    }

    public T get(int index) {
        if (index < 0 || index >= buffer.length) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (buffer.length - 1));
        }
        return buffer[index];
    }

    public T getFirst() {
        return buffer[0];
    }

    public T getLast() {
        return buffer[buffer.length - 1];
    }

    public int size() {
        return buffer.length;
    }

    public boolean allNull() {
        for (T t : buffer) {
            if (t != null) {
                return false;
            }
        }
        return true;
    }

    public boolean allNotNull() {
        for (T t : buffer) {
            if (t == null) {
                return false;
            }
        }
        return true;
    }

    public T[] getBuffer() {
        return buffer;
    }

    public void setBuffer(T[] buffer) {
        this.buffer = buffer;
    }
}
