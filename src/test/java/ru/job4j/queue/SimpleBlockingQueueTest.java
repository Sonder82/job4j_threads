package ru.job4j.queue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@Disabled
class SimpleBlockingQueueTest {

    @Test
    void whenPutAndTake() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> queue.offer(1));
        producer.start();
        Thread consumer = new Thread(() -> list.add(queue.poll()));
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).hasSize(1);
    }
}