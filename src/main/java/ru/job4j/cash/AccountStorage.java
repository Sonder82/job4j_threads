package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    /**
     * Поле хранилище Account's.
     */
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    /**
     * Метод добавляет новых пользователей
     *
     * @param account объект Account.
     * @return результат добавления в виде "boolean" логики
     */
    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (getById(account.getId()).isEmpty()) {
            rsl = true;
            accounts.put(account.getId(), account);
        }
        return rsl;
    }

    /**
     * Метод обновляет данные Account
     *
     * @param account объект Account.
     * @return результат обновления в виде "boolean" логики
     */
    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (getById(account.getId()).isPresent()) {
            rsl = true;
            accounts.put(account.getId(), account);
        }
        return rsl;
    }

    /**
     * Метод удаляет Account из хранилища.
     *
     * @param id ключ, к которому привязан Account.
     * @return результат удаления в виде "boolean" логики
     */
    public synchronized boolean delete(int id) {
        boolean rsl = false;
        if (getById(id).isPresent()) {
            rsl = true;
            accounts.remove(id);
        }
        return rsl;
    }

    /**
     * Метод выполняет поиск Account в хранилище по Id.
     *
     * @param id id ключ, к которому привязан Account.
     * @return объект Account
     */
    public synchronized Optional<Account> getById(int id) {
        return accounts.values().stream()
                .filter(account -> account.getId() == id)
                .findFirst();
    }

    /**
     * Метод выполняет перевод средств с одного Account на другой.
     *
     * @param fromId Id Account отправителя
     * @param toId   Id Account получателя
     * @param amount количество переводимых средств
     * @return результат перевода в виде "boolean" логики
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> source = getById(fromId);
        Optional<Account> dest = getById(toId);
        if (source.isPresent() && dest.isPresent()
                && source.get().getAmount() >= amount) {
            rsl = true;
            source.get().setAmount(source.get().getAmount() - amount);
            dest.get().setAmount(dest.get().getAmount() + amount);
        }
        return rsl;
    }
}
