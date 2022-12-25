package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    private static int rowSum(int[][] matrix, int row) {
        int sum = 0;
        for (int cell = 0; cell < matrix.length; cell++) {
            sum += matrix[row][cell];
        }
        return sum;
    }

    private static int colSum(int[][] matrix, int cell) {
        int sum = 0;
        for (int row = 0; row < matrix.length; row++) {
            sum += matrix[row][cell];
        }
        return sum;
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = new Sums(rowSum(matrix, i), colSum(matrix, i));
        }
        return rsl;
    }

    public static CompletableFuture<Integer> getTaskForRow(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(
                () -> rowSum(matrix, row)
        );
    }

    public static CompletableFuture<Integer> getTaskForColumn(int[][] matrix, int cell) {
        return CompletableFuture.supplyAsync(
                () -> colSum(matrix, cell)
        );
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Integer>> futuresRow = new HashMap<>();
        Map<Integer, CompletableFuture<Integer>> futuresCol = new HashMap<>();
        for (int keyRow = 0; keyRow < matrix.length; keyRow++) {
            futuresRow.put(keyRow, getTaskForRow(matrix, keyRow));
        }
        for (int keyCol = 0; keyCol < matrix.length; keyCol++) {
            futuresCol.put(keyCol, getTaskForColumn(matrix, keyCol));
        }
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = new Sums(futuresRow.get(i).get(), futuresCol.get(i).get());
        }
        return rsl;
    }
}


