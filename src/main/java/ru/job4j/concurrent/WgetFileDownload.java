package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetFileDownload implements Runnable {
    private final String url;
    private final int speed;

    public WgetFileDownload(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public String getUrl() {
        return url;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        String file = getUrl();
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long startTime = (System.currentTimeMillis());
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long loadTime = (System.currentTimeMillis() - startTime);
                if (loadTime > getSpeed()) {
                    Thread.sleep(loadTime - getSpeed());
                }

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Arguments must contains two parameters");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetFileDownload(url, speed));
        wget.start();
        wget.join();
    }
}
