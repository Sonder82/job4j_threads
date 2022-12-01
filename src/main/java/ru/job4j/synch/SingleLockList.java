package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * В этом классе создается коллекция, которая будет корректно работать в многопоточной среде.
 * То есть сама коллекция будет общим ресурсом между нитями.
 *
 * @param <T> тип данных
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    /**
     * Поле коллекция
     */
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
       return list.get(index);
    }

    /**
     * Любое структурное изменение, внесенное в итератор,
     * влияет на скопированную коллекцию, а не на исходную коллекцию.
     * Таким образом, исходная коллекция остается структурно неизменной.
     * @return итератор копии листа
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    /**
     * Метод выполняет копирование исходной коллекции.
     * @param origin коллекция "исходник"
     * @return возвращает копию коллекции "исходника"
     */
    private List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);
    }
}
