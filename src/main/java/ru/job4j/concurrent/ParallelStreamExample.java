package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 6, 4, 5);
        list.stream().parallel()
                .forEachOrdered(System.out::println);
        System.out.println(list.stream().isParallel());
        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        stream.sequential();
        System.out.println(stream.isParallel());
    }
}
