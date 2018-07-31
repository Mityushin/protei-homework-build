package ru.protei;

import ru.protei.annotation.AllowMagicComment;
import ru.protei.elements.ElementWithValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Homework04 {

    private static void printAssignableFields(Object object, Class checkClass) {

        for (Class clazz = object.getClass();
             (clazz != null) && (clazz != Object.class);
             clazz = clazz.getSuperclass()) {

            for (Field field : clazz.getDeclaredFields()) {

                if (field.getType().isAssignableFrom(checkClass)) {

                    System.out.println(field.toString());

                }
            }
        }
    }

    private static void printNumberFields(Object object) {
        printAssignableFields(object, Number.class);
    }

    private static void printStringFields(Object object) {
        printAssignableFields(object, String.class);
    }

    private static void printAnnotatedFieldsAndMethods(Class clazz) {

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AllowMagicComment.class)) {
                System.out.println(field.toString() + field.getAnnotation(AllowMagicComment.class).value());
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AllowMagicComment.class)) {
                System.out.println(method.toString() + method.getAnnotation(AllowMagicComment.class).value());
            }
        }

    }

    private static void printExtendedAnnotatedFieldsAndMethods(Object object) {
        for (Class clazz = object.getClass();
             (clazz != null) && (clazz != Object.class);
             clazz = clazz.getSuperclass()) {
            printAnnotatedFieldsAndMethods(clazz);
        }
    }

    private static void printGenericFieldsAndMethods(Object object) {

        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.getType().equals(field.getGenericType())) {
                System.out.println(field.getName() + " " + field.getGenericType().getTypeName());
            }
        }
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (!method.getReturnType().equals(method.getGenericReturnType())) {
                System.out.println(method.getName() + " " + method.getGenericReturnType().getTypeName());
            }
        }

    }

    public static void main(String[] args) {

        ElementWithValue element = new ElementWithValue<>("Hello");

        System.out.println("Printing Number fields...");
        Homework04.printNumberFields(element);

        System.out.println("\nPrinting String fields...");
        Homework04.printStringFields(element);

        System.out.println("\nPrinting Extended Annotated fields and methods...");
        Homework04.printExtendedAnnotatedFieldsAndMethods(element);

        System.out.println("\nPrinting Generic fields and methods...");
        Homework04.printGenericFieldsAndMethods(element);
    }
}
