package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ParallelIndexFoundTest {

    @Test
    void whenArrayWithString() {
        String[] list = {"hello", "buy"};
        assertThat(ParallelIndexFound.sort(list, "buy")).isEqualTo(1);
    }

    @Test
    void whenArrayNotContainElement() {
        String[] list = {"hello", "buy"};
        assertThat(ParallelIndexFound.sort(list, "helo")).isEqualTo(-1);
    }

    @Test
    void whenArrayMoreThen10() {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertThat(ParallelIndexFound.sort(list, 3)).isEqualTo(2);
    }
}