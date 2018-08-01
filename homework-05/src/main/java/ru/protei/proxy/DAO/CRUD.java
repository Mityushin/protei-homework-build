package ru.protei.proxy.DAO;

import ru.protei.proxy.annotation.LogTimingMetric;

public interface CRUD<T> {
    boolean create(T t);
    @LogTimingMetric
    boolean update(T t);
    boolean delete(T t);
}
