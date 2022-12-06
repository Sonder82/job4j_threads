package ru.job4j.queue;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenPut5AndTake5() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            try {
                for (int index = 0; index < 5; index++) {
                    queue.offer(index);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int index = 0; index < 5; index++) {
                    list.add(queue.poll());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    void whenPut10AndTake7() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(7);
        Thread producer = new Thread(() -> {
            try {
                for (int index = 0; index < 10; index++) {
                    queue.offer(index);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int index = 0; index < 7; index++) {
                    list.add(queue.poll());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list.size()).isEqualTo(7);
    }
}