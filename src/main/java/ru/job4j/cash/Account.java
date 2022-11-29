package ru.job4j.cash;

import java.util.Objects;

public class Account {
    /**
     * Поле уникальный идентификатор
     */
    private final int id;
    /**
     * Поле баланс пользователя
     */
    private int amount;

    public Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id && amount == account.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "Account{"
                + "id=" + id
                + ", amount=" + amount
                + '}';
    }
}
