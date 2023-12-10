package avltree;

public class Node<T> {
    private T value;
    private int height;
    private Node<T> leftNode;
    private Node<T> rightNode;

    public Node(T value) {
        this.value = value;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeftNode(Node<T> leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node<T> rightNode) {
        this.rightNode = rightNode;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public int getHeight() {
        return height;
    }

    public Node<T> getLeftNode() {
        return leftNode;
    }

    public Node<T> getRightNode() {
        return rightNode;
    }
}
