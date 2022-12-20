package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


/**
 * Класс реализует работу по поиску индекса в массиве объектов.
 * Будем наследоваться от {@link RecursiveTask}.
 * {@link RecursiveTask} будет возвращать Integer, это будет наш искомый индекс.
 * @param <T> обобщенный тип
 */
public class ParallelIndexFound<T> extends RecursiveTask<Integer> {
    /**
     * Поле массива, где ищем индекс
     */
    private final T[] array;
    /**
     * Поле диапазон "поиска от"
     */
    private final int from;
    /**
     * Поле диапазон "поиск до"
     */
    private final int to;
    /**
     * Поле элемент, чей индекс ищем в массиве.
     */
    private final T element;

    public ParallelIndexFound(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    /**
     * Метод наследник {@link RecursiveTask},
     * выполняет задачу по нахождению индекса.
     * В зависимости от размера массива, будем выполнять
     * поиск линейно(если малый размер массива),
     * или выполнять рекурсивный поиск, разделяя массив.
     * @return значение индекса элемента, который ищем в массиве
     */
    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return computeDirectly();
        }
            int mid = (to - from) / 2;
            ParallelIndexFound<T> leftFound = new ParallelIndexFound<>(array, from, mid, element);
            ParallelIndexFound<T> rightFound = new ParallelIndexFound<>(array, mid + 1, to, element);
            leftFound.fork();
            rightFound.fork();
            Integer leftResult = leftFound.join();
            Integer rightResult = rightFound.join();
            return Math.max(leftResult, rightResult);

    }

    /**
     * Метод выполняет линейный поиск индекса элемента в массиве.
     * Воспользуемся циклом, чтобы искать в заданном диапазоне
     * {@link ParallelIndexFound#from} {@link ParallelIndexFound#to}
     * @return индекс элемента.
     */
    private Integer computeDirectly() {
        int result = -1;
        for (int index = from; index <= to; index++) {
            if (array[index].equals(element)) {
                result = index;
                break;
            }
        }
        return result;
    }

    /**
     * Метод выполняет отправку задачи в пул потоков.
     * Метод{@link ForkJoinPool#invoke(ForkJoinTask)}
     * разветвляет задачу и ожидает результата.
     * @param array массив
     * @param element элемент поиска в массиве
     * @param <T> обобщение
     * @return индекс искомого элемента в массиве
     */
    public static <T> int sort(T[]array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexFound<>(array, 0, array.length - 1, element));
    }
}
