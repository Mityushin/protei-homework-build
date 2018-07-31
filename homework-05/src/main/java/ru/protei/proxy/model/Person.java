package ru.protei.proxy.model;

public class Person {
    private final int id;
    private String name;

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }
}
