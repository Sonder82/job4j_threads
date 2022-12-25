package ru.job4j.async;

import ru.job4j.async.AsyncRunMain;
import ru.job4j.async.AsyncSupplyMain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncThenComposeMain {
    public static void thenComposeExample() throws ExecutionException, InterruptedException {
        CompletableFuture<String> result = AsyncRunMain.goToTrash()
                .thenCompose(a -> AsyncSupplyMain.buyProduct("Milk"));
        result.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        thenComposeExample();
    }
}
