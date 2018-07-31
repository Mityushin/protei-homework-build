package ru.protei.proxy.DAO;

public interface CRUD<T> {
    boolean create(T t);
    boolean update(T t);
    boolean delete(T t);
}
