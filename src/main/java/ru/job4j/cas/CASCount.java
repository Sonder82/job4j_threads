package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int temp = count.get() + 1;
        int ref;
        do {
            ref = count.get();
            if (ref == 0) {
                throw new IllegalStateException("Count is not implement.");
            }
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        if (count.get() == 0) {
            throw new IllegalStateException("Count is not implement.");
        }
        return count.get();
    }
}
