package avltree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new AvlTree<>();
//        tree.insert(5);
//        tree.insert(9);
//        tree.insert(6);
//        tree.insert(7);
//        tree.insert(8);
//        tree.insert(4);
//        tree.insert(3);
//
//        tree.traverse();
//
//        tree.delete(6);

        tree.insert(6);
        tree.insert(4);
        tree.insert(7);
        tree.insert(3);
        tree.insert(5);

        System.out.println("delete");
        tree.delete(7);
        tree.traverse();
    }
}
