package ru.protei.elements;

import org.junit.Before;
import org.junit.Test;
import ru.protei.elements.ElementWithValue;

import static org.junit.Assert.*;

public class ElementWithValueTest {

    private static ElementWithValue element;

    @Before
    public void setUp() throws Exception {
        element = new ElementWithValue<>("hello");
    }

    @Test
    public void getValue() {
        assertEquals(element.getValue(), "hello");
    }

    @Test
    public void getId() {
        assertEquals(element.getId(), 10);
    }
}