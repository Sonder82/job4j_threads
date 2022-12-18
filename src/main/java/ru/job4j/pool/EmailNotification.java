package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс реализует сервис для рассылки почты.
 */
public class EmailNotification {
    /**
     * Поле заданное количество нитей в пуле.
     * Зависит от ядер процессора.
     */
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * Метод создает задачу,
     * которая будет создавать данные
     * для пользователя и передавать
     * их в метод{@link EmailNotification#send(String, String, String)} доставить
     * @param user пользователь.
     */
    public void emailTo(User user) {
        pool.submit(() -> send(subject(user), body(user), user.email()));
    }

    /**
     * Метод формирует шаблон,
     * в котором создается уведомление
     * по его имени и email
     * @param user пользователь
     * @return строковый шаблон
     */
    private String subject(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Notification ")
                .append(user.userName())
                .append(" to email ")
                .append(user.email());
        return stringBuilder.toString();

    }

    /**
     * Метод формирует шаблон,
     * в котором прописывается событие
     * для пользователя
     * @param user пользователь
     * @return строковый шаблон
     */
    private String body(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Add a new event to ")
                .append(user.userName());
        return stringBuilder.toString();
    }

    /**
     * Метод завершает работу пула,
     * давая всем задачам в очереди отработать до конца.
     * После чего закроется сам пул.
     *
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
