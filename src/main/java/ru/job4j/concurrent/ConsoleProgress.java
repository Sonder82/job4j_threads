package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            char[] process = "-\\|/".toCharArray();
            for (char symbol : process) {
                try {
                    System.out.print("\rLoading: " + symbol);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
