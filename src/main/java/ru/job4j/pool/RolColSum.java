package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

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

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = rowColSum(matrix, i, i);
        }
        return rsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int row, int cell) {
        return CompletableFuture.supplyAsync(
                () -> rowColSum(matrix, row, cell)
        );
    }

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


