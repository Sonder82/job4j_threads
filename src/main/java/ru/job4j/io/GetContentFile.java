package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Класс реализует работу по получению данных файла.
 */
public final class GetContentFile {

    private final File file;

    public GetContentFile(File file) {
        this.file = file;
    }

    /**
     * Метод выполняет чтение файла.
     * В зависимости от предиката, выполняются действия с символами.
     * С помощью метода избавимся от копирования кода.
     * В дальнейшем, просто будем вызывать метод и в параметры указывать
     * нужные нам предикаты.
     * Шаблон стратегия.
     * @param predicate условие фильтрации
     * @return строки контента
     */
    private String content(Predicate<Character> predicate) {
        StringBuilder output = new StringBuilder();
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            int data;
            while ((data = inputStream.read()) != -1) {
                if (predicate.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();

    }

    public synchronized String getContent() {
        return content(character -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }
}
