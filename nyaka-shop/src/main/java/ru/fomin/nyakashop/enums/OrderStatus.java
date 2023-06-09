package ru.fomin.nyakashop.enums;

public enum OrderStatus {
    NEW("Новый"),
    CANCELED("Отменён"),
    COLLECTING("Собирается"),
    DELIVERING("Доставляется"),
    FINISHED("Выполнен");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
