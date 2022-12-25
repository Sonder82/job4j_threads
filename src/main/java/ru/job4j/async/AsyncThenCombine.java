package ru.job4j.async;

import ru.job4j.async.AsyncSupplyMain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncThenCombine {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static void thenCombineExample() throws InterruptedException, ExecutionException {
        CompletableFuture<String> result = AsyncSupplyMain.buyProduct("Milk")
                .thenCombine(AsyncSupplyMain.buyProduct("Bread"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        thenCombineExample();
    }
}
