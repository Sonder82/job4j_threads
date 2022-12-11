package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void whenIncrement() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(() -> {
            for (int index = 0; index < 5; index++) {
                casCount.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int index = 0; index < 5; index++) {
                casCount.increment();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
       assertThat(casCount.get()).isEqualTo(10);
    }
}