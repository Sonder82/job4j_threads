package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Класс описывает работу скачивания файла из сети с ограничением по скорости скачки.
 * @author Aleksey Novoselov
 * @version 1.0
 */
public class WgetFileDownload implements Runnable {
    /**
     * Будем измерять скорость скачивания - пусть это будут байты в секунду.
     */
    private final static long ONE_SECOND  = 1000;

    private long currentTime  = System.currentTimeMillis();
    /**
     * Поле название файла из сети
     */
    private final String url;
    /**
     * Поле скорость скачки
     */
    private final int speed;
    /**
     * Поле название файла куда загружаются данные
     */
    private final String filePath;

    public WgetFileDownload(String url, int speed, String filePath) {
        this.url = url;
        this.speed = speed;
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public int getSpeed() {
        return speed;
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * В методе описывается реализация загрузки содержимого файла в байтах.
     * Выполняется проверка количества скачанных байтов.
     * При достижении или превышении требуемого количества байт,
     * (например при скорости 1мб/с - это 1048576 байт)
     * происходит расчет затраченного времени, и сравнивается с секундой.
     * Если времени потребовалось меньше секунды, то нить переводим в режим ожидания.
     * Время режима ожидания будет равно разности времени загрузки к одной секунде.
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(getUrl()).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(getFilePath())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTime = currentTime;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= getSpeed()) {
                    long loadTime = (currentTime - startTime);
                    if (loadTime < ONE_SECOND) {
                        Thread.sleep(ONE_SECOND - loadTime);
                    }
                    downloadData = 0;
                    startTime = currentTime;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод выполняет валидацию заданных аргументов
     * @param args аргументы
     */
    private static void validation(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Arguments must contains three parameters");
        }
        String digital = args[1];
        if (!digital.chars()
                .allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Speed of downloads must be digital");
        }
        String pathFile = args[2];
        String extension = pathFile.substring(pathFile.lastIndexOf(".") + 1);
        if (!extension.endsWith("dat")) {
            throw new IllegalArgumentException("File must contains extension .dat");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validation(args);
        Thread wget = new Thread(new WgetFileDownload(args[0], Integer.parseInt(args[1]), args[2]));
        wget.start();
        wget.join();
    }
}
