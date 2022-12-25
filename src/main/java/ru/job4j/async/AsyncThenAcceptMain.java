package ru.job4j.async;

import ru.job4j.async.AsyncSupplyMain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncThenAcceptMain {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static void thenAcceptExample() throws InterruptedException, ExecutionException {
        CompletableFuture<String> bp = AsyncSupplyMain.buyProduct("Молоко");
        bp.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник"));
        iWork();
        System.out.println("Куплено: " + bp.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        thenAcceptExample();
    }
}
