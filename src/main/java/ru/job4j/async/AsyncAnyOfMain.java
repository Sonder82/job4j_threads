package ru.job4j.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncAnyOfMain {
    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return name + ", моет руки";
                }
        );
    }
    public static void anyOfExample() throws InterruptedException, ExecutionException {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Иван"), whoWashHands("Борис")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(3);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        anyOfExample();
    }
}
