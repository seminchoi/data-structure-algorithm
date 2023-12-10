package avltree;

public interface Tree<T> {
    void insert(T node);
    void traverse();
    void delete(T value);
}
