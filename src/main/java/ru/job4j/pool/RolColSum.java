package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс выполняет задачу по подсчету суммы по строкам и столбцам квадратной матрицы
 */
public class RolColSum {

    /**
     * Метод выполняет подсчет суммы в i строке и в i столбце квадратной матрицы.
     * @param matrix квадратная матрица
     * @param row i строка
     * @param cell i столбец
     * @return результат вычисления в виде объекта Sums.
     */
    private static Sums rowColSum(int[][] matrix, int row, int cell) {
        int sumRow = 0;
        for (int i = 0; i < matrix.length; i++) {
            sumRow += matrix[row][i];
        }
        int sumCol = 0;
        for (int i = 0; i < matrix[row].length; i++) {
            sumCol += matrix[i][cell];
        }
        return new Sums(sumRow, sumCol);
    }

    /**
     * Метод реализует итоговый результата подсчета сумм.
     * Подсчет производится последовательно.
     * @param matrix квадратная матрица
     * @return массив объектов Sums
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = rowColSum(matrix, i, i);
        }
        return rsl;
    }

    /**
     * Этот метод возвращает CompletableFuture<Sums>. Результатом будет
     * выполненная задача по подсчету сумм i строки и i столбца.
     * @param matrix квадратная матрица
     * @param row ряд
     * @param cell столбец
     * @return возвращает CompletableFuture<Sums>
     */
    public static CompletableFuture<Sums> getTask(int[][] matrix, int row, int cell) {
        return CompletableFuture.supplyAsync(
                () -> rowColSum(matrix, row, cell)
        );
    }

    /**
     * Метод реализует итоговый подсчет сумм.
     * Подсчет выполнен с использованием асинхронной задачи {@link#getTask}
     * @param matrix квадратная матрица
     * @return массив объектов Sums
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i, i));
        }
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = futures.get(i).get();
        }
        return rsl;
    }
}


