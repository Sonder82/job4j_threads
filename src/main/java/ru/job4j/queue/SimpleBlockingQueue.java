package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  Класс реализует собственную версию bounded blocking queue.
 *  Это блокирующая очередь, ограниченная по размеру.
 *  В данном шаблоне Producer помещает данные в очередь,
 *  а Consumer извлекает данные из очереди.
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int bufferSize;

    public SimpleBlockingQueue(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Метод помещает значение в очередь
     * @param value значение
     * @throws InterruptedException исключение
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == bufferSize) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    /**
     * Метод возвращает значение из очереди
     * @return значение
     * @throws InterruptedException исключение
     */
    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
           wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
