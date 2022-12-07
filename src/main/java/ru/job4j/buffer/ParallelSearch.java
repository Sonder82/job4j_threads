package ru.job4j.buffer;


import ru.job4j.queue.SimpleBlockingQueue;

/**
 * Класс воспроизводит механизм остановки потребителя consumer,
 * когда производитель producer закончил свою работу.
 *
 * Воспользуемся методом interrupt, и сообщим нити consumer,
 * что ей необходимо завершить работу.
 * Этот метод выставляет флаг прерывания.
 *
 * Состояние флага будем проверять в цикле while.
 * Чтобы проверить состояние флага, можно воспользоваться методом isInterrupted().
 * Данный метод возвращает состояние флага прерывания и не меняет его состояние, то есть делает только проверку.
 *
 * Дальше обязательно ждем завершения работы producer(метод join).
 * И прерываем методом interrupt работу consumer.
 * Обязательно ждем завершения работы consumer(метод join)
 *
 */
public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final Thread consumer = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    System.out.println(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();

        Thread producer = new Thread(() -> {
            for (int index = 0; index != 3; index++) {
                try {
                    queue.offer(index);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
    }
}
