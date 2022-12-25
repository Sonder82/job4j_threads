package ru.job4j.async;

import ru.job4j.async.AsyncSupplyMain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncThenApplyMain {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static void thenApplyExample() throws InterruptedException, ExecutionException {
        CompletableFuture<String> bp = AsyncSupplyMain.buyProduct("Молоко")
                        .thenApply((product) -> "Сын: Я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bp.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        thenApplyExample();
    }
}
