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
     * @return "boolean" логику при добавлении данных в память.
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Метод обновляет данные модели.
     * Перед обновлением проверяем version модели и в кеше.
     * Обновлять только в случае равных version.
     * При обновлении увеличиваем version в кеше на единицу.
     * @param model модель
     * @return "boolean" логику при обновлении данных в памяти.
     */
    public boolean update(Base model) {

        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() != model.getVersion()) {
                    throw new OptimisticException("Versions are not equal");
            }
            Base updated = new Base(model.getId(), value.getVersion() + 1);
            updated.setName(model.getName());
            return updated;
        }) != null;
    }

    /**
     * Метод удаляет данные из памяти
     *
     * @param model данные
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
