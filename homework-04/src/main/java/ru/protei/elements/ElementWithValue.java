package ru.protei.elements;

import ru.protei.annotation.AllowMagicComment;

public class ElementWithValue<E> extends Element {

    @AllowMagicComment(value = "Element value")
    private final E value;

    public ElementWithValue(E value) {
        super();
        this.value = value;
    }

    @AllowMagicComment(value = "Getter for value")
    public E getValue() {
        return value;
    }
}
