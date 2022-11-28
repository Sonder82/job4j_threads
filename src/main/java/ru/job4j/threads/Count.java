package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс выполняет задачу связанную с параллельным увеличением счетчика.
 */
@ThreadSafe
public class Count {
    /**
     * Поле счетчик
     */
    @GuardedBy("this")
    private int value;

    /**
     * Метод увеличивает внутреннее поле на 1
     */
    public synchronized void increment() {
        this.value++;
    }

    /**
     * Метод получает значение поля
     * @return значение поля
     */
    public synchronized int get() {
        return this.value;
    }
}
