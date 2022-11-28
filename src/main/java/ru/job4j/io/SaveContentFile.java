package ru.job4j.io;

import java.io.*;

public final class SaveContentFile {

    private final File file;

    public SaveContentFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
           for (int i = 0; i < content.length(); i += 1) {
               outputStream.write(content.charAt(i));
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
