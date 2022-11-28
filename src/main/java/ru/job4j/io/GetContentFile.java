package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class GetContentFile {

    private final File file;

    public GetContentFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> predicate) {
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
  /**
    public synchronized String getContent() {
        return content(character -> { });
    }
*/
    public synchronized String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }
}
