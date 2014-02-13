package Repos;

import Interfaces.IteratorInterface;

public class Iterator<type> implements IteratorInterface<type> {

    private type[] items;
    private int index;

    public Iterator(type[] items) {
        this.items = items;
        this.index = 0;
    }

    public boolean hasNext() {
        return items[index] != null;
    }

    public type next() {
        type next = items[index];

        index += 1;

        return next;
    }
}
