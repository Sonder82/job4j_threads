package ru.job4j.synchron;

public class Cache {
    private static Cache cache;

    /**
     * Пример для не явного указания монитора.
     * Ключевое слово synchronized записывается после модификатора доступа.
     * @return объект Cache
     */
    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    /**
     * Пример для явного указания монитора.
     * В этом случае использован блок synchronized
     * @return объект Cache
     */
    public static Cache instOfExampleTwo() {
        synchronized (Cache.class) {
            if (cache == null) {
                cache = new Cache();
            }
            return cache;
        }
    }
}
