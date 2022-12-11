package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenTryAddTwoTime() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        assertThat(cache.add(new Base(1, 1))).isFalse();
    }

    @Test
    void whenTryAdd() {
        Cache cache = new Cache();
        assertThat(cache.add(new Base(1, 1))).isTrue();
    }

    @Test
    void whenTryUpdateWithDiffVersion() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    void whenAddThenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertThat(cache.update(base)).isTrue();
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.add(base)).isTrue();
    }
}