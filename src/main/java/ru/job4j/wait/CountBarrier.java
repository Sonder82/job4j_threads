package ru.job4j.wait;

/**
 * Класс выполняет блокирование по условию счетчика.
 */
public class CountBarrier {
    /**
     * Монитор
     */
    private final Object monitor = this;
    /**
     * содержит количество вызовов метода count().
     */
    private final int total;
    /**
     * счетчик
     */
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод будет изменять состояние программы.
     * Изменение состояния будет видно с помощью счетчика count.
     * Метод notifyAll будет будить все нити, которые ждали изменения состояния.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();

        }
    }

    /**
     * Метод выполняет проверку количества изменения состояния программы.
     * Метод wait переводит нить в состояние ожидания, если программа не может дальше выполняться
     */
    public void await() {
        synchronized (monitor) {
    while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
