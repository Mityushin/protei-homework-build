package ru.protei.elements;

import ru.protei.annotation.AllowMagicComment;

public class Element {

    @AllowMagicComment(value = "Element id")
    private final Integer id;

    public Element() {
        this.id = 10;
    }

    @AllowMagicComment(value = "Getter for id")
    public int getId() {
        return id;
    }
}
