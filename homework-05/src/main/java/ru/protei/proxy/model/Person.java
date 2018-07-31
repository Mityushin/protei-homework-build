package ru.protei.proxy.model;

public class Person {
    private int id;
    private String name;

    public Person() {
    }

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Person setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }
}
