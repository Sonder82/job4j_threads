package ru.job4j.queue;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenPut5AndTake5() throws InterruptedException {
        final List<Integer> list = new ArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            try {
                for (int index = 0; index < 5; index++) {
                    queue.offer(index);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int index = 0; index < 5; index++) {
                    list.add(queue.poll());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    void whenPut10AndTake7() throws InterruptedException {
        final List<Integer> list = new ArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(7);
        Thread producer = new Thread(() -> {
            try {
                for (int index = 0; index < 10; index++) {
                    queue.offer(index);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int index = 0; index < 7; index++) {
                    list.add(queue.poll());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(list.size()).isEqualTo(7);
    }

    @Test
    void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            IntStream.range(0, 7)
                    .forEach(value -> {
                        try {
                            queue.offer(value);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
        });

        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                try {
                        buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).hasSize(7)
        .contains(0, 1, 2, 3, 4, 5, 6);
    }
}