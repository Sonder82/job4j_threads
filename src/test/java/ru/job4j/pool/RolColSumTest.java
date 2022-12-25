package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pool.RolColSum.asyncSum;
import static ru.job4j.pool.RolColSum.sum;

class RolColSumTest {

    @Test
    void whenCheckSumOfColumn() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = sum(array);
        int result = sums[0].getColSum();
        assertThat(result).isEqualTo(12);
    }

    @Test
    void whenCheckSumOfRow() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = sum(array);
        int result = sums[1].getRowSum();
        assertThat(result).isEqualTo(15);
    }

    @Test
    public void whenSums() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = sum(array);
        Sums[] expected = new Sums[]{
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(sums).isEqualTo(expected);
    }

    @Test
    public void whenAsyncSums() throws ExecutionException, InterruptedException {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = asyncSum(array);
        Sums[] expected = new Sums[]{
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(sums).isEqualTo(expected);
    }
}