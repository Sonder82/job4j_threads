package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class ThreadPool {
    /**
     * Поле количества ядер в системе
     */
    private final int countCore = Runtime.getRuntime().availableProcessors();
    /**
     * Поле список потоков
     */
    private final List<Thread> threads = new LinkedList<>();
    /**
     * Поле блокирующая очередь с задачами для потоков
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(2);

    /**
     * В Конструкторе при создании объекта {@link ThreadPool} задается
     * определенное количество нитей,
     * равное числу ядер устройства {@link ThreadPool#countCore}.
     * У каждой нити в методе {@link Thread#run()}
     * необходимо взять задачу для выполнения из блокирующей очереди и запустить ее.
     * Далее запускаем нить к исполнению {@link Thread#start()}
     * Добавляем нить в список нитей {@link ThreadPool#threads}
     */
    public ThreadPool() {
        for (int index = 0; index < countCore; index++) {
            Thread thread = new Thread(() -> {
                while (!Thread.interrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    /**
     * Метод должен добавлять задачи в блокирующую очередь tasks
     * @param job задача
     */
    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    /**
     * Метод завершит все запущенные задачи
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int index = 0; index < threadPool.countCore; index++) {
            threadPool.work(() ->
                    System.out.println(Thread.currentThread().getName()));
        }
        threadPool.shutdown();
    }
}
