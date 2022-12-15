package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(2);

    /**
     * Метод должен добавлять задачи в блокирующую очередь tasks
     * @param job задача
     */
    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Метод завершит все запущенные задачи
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
