package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;


class SingleLockListTest {

    @Test
    void whenIt() {
        ArrayList<Integer> init = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        list.add(1);
        Iterator<Integer> it = list.iterator();
        list.add(2);
        assertThat(it.next()).isEqualTo(1);
    }

    @Test
    void whenAdd() throws InterruptedException {
        ArrayList<Integer> init = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(init);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(Set.of(1, 2)).isEqualTo(rsl);
    }
}