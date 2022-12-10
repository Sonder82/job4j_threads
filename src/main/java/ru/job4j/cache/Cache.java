package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс реализует работу неблокирующего кэша.
 * Коллекции Map используют для создания кешей.
 * Кеш - это элемент программы позволяющий увеличить
 * скорость работы за счет хранения данных в памяти.
 * Например, если данные о пользователе хранятся в базе данных
 * и мы часто их используем, то, чтобы увеличить скорость работы
 * можно загрузить информацию о пользователях в память и читать сразу из памяти.
 */
public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Метод добавляет данные в память(memory).
     *
     * @param model данные
     * @return "булеву" логику при добавлении данных в память.
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
    public boolean update(Base model) {

        return memory.computeIfPresent(model.getId(), (key, value) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                try {
                    throw new OptimisticException("Versions are not equal");
                } catch (OptimisticException e) {
                    e.printStackTrace();
                }
            }
            return model;
        }) != null;

    }
*/
    /**
     * Метод удаляет данные из памяти
     *
     * @param model данные
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
