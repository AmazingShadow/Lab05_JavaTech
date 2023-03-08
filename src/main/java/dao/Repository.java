package dao;

import java.util.List;

public interface Repository <T, V> {
    boolean add(T item);
    T get(V id);
    List<T> getAll();
    boolean update(T item);
    boolean remove(V id);
}
