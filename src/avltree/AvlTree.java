package avltree;

public class AvlTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public void insert(T value) {
        root = insert(root, value);
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            //TODO : 이 방식으로 Reference 참고가 가능한지?
            return new Node<>(value);
        }

        if (node.getValue().compareTo(value) > 0) {
            node.setLeftNode(insert(node.getLeftNode(), value));
        }
        if (node.getValue().compareTo(value) < 0) {
            node.setRightNode(insert(node.getRightNode(), value));
        }

        updateHeight(node);

        node = settleViolation(node, value);

        return node;
    }

    private Node<T> settleViolation(Node<T> node, T value) {
        final int balance = getBalance(node);

        if (caseLeftLeft(balance, node, value)) {
            System.out.println("LL");
            return rotateRight(node);
        }
        if (caseRightLeft(balance, node, value)) {
            Node<T> rightNode = rotateRight(node.getRightNode());
            node.setRightNode(rightNode);
            System.out.println("RL");
            return rotateLeft(node);
        }
        if (caseRightRight(balance, node, value)) {
            System.out.println("RR");
            return rotateLeft(node);
        }
        if (caseLeftRight(balance, node, value)) {
            Node<T> leftNode = rotateLeft(node.getLeftNode());
            node.setLeftNode(leftNode);
            System.out.println("LR");
            return rotateRight(node);
        }

        return node;
    }

    private int getBalance(Node<T> node) {
        return height(node.getLeftNode()) - height(node.getRightNode());
    }

    private boolean caseLeftLeft(final int balance, final Node<T> node, final T value) {
        final Node<T> leftNode = node.getLeftNode();
        return balance > 1 && leftNode.getValue().compareTo(value) > 0;
    }

    private boolean caseRightLeft(final int balance, final Node<T> node, final T value) {
        final Node<T> rightNode = node.getRightNode();
        return balance < -1 && rightNode.getValue().compareTo(value) > 0;
    }

    private boolean caseRightRight(final int balance, final Node<T> node, final T value) {
        final Node<T> rightNode = node.getRightNode();
        return balance < -1 && rightNode.getValue().compareTo(value) < 0;
    }

    private boolean caseLeftRight(final int balance, final Node<T> node, final T value) {
        final Node<T> leftNode = node.getLeftNode();
        return balance > 1 && leftNode.getValue().compareTo(value) < 0;
    }

    private void updateHeight(Node<T> node) {
        node.setHeight(
                Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1
        );
    }

    private int height(Node<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }


    public Node<T> rotateRight(Node<T> node) {
        final Node<T> templateNode = node.getLeftNode();
        final Node<T> templateRightNode = templateNode.getRightNode();

        templateNode.setRightNode(node);
        node.setLeftNode(templateRightNode);

        updateHeight(node);
        updateHeight(templateNode);

        return templateNode;
    }

    public Node<T> rotateLeft(Node<T> node) {
        final Node<T> templateNode = node.getRightNode();
        final Node<T> templateLeftNode = templateNode.getLeftNode();

        templateNode.setLeftNode(node);
        node.setRightNode(templateLeftNode);

        updateHeight(node);
        updateHeight(templateNode);

        return templateNode;
    }

    @Override
    public void traverse() {
        preOrder(root);
    }

    private void preOrder(final Node<T> node) {
        if (node == null) {
            return;
        }

        preOrder(node.getLeftNode());
        System.out.println(node.getValue());
        preOrder(node.getRightNode());
    }

    @Override
    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return node;
        }

        if (node.getValue().compareTo(value) > 0) {
            node.setLeftNode(delete(node.getLeftNode(), value));
        }
        if (node.getValue().compareTo(value) < 0) {
            node.setRightNode(delete(node.getRightNode(), value));
        }
        if (node.getValue().compareTo(value) == 0) {
            return delete(node);
        }

        updateHeight(node);

        return settleDeletion(node);
    }

    private Node<T> delete(Node<T> node) {
        if (isLeaf(node)) {
            return null;
        }

        //삭제하려는 노드가 하나의 자식 노드를 가진 경우
        //리프노드인 경우는 위 조건문에서 메소드를 탈출하게 됨
        //왼쪽이나 오른쪽이 없으면 이어주기만 하면 됨
        //균형 트리 이므로 한쪽 자식이 없으면 다른 쪽 자식은 하나만 있음
        if (node.getLeftNode() == null) {
            return node.getRightNode();
        }
        if (node.getRightNode() == null) {
            return node.getLeftNode();
        }

        //자식을 두 개 이상 가지고 있을 경우
        Node<T> templateNode = getPredecessor(node.getLeftNode());
        node.setValue(templateNode.getValue());
        //루트 노드의 값과 왼쪽 서브트리에서 가장 큰 값을 바꾼 후 다시 delete 재귀 호출
        node.setLeftNode(delete(node.getLeftNode(), templateNode.getValue()));
        return node;
    }

    private boolean isLeaf(final Node<T> node) {
        return height(node) == 0;
    }

    private Node<T> getPredecessor(Node<T> node) {
        while (node.getRightNode() != null) {
            node = node.getRightNode();
        }
        return node;
    }

    private Node<T> settleDeletion(Node<T> node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.getLeftNode()) < 0) {
                System.out.println("LR");
                node.setLeftNode(rotateLeft(node.getLeftNode()));
            }
            else {
                System.out.println("LL");
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            if(getBalance(node.getRightNode()) > 0) {
                System.out.println("RL");
                node.setRightNode(rotateRight(node.getRightNode()));
            }
            else {
                System.out.println("RR");
            }
            return rotateLeft(node);
        }

        return node;
    }
}
